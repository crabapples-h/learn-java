package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.system.dao.jpa.MenusRepository;
import cn.crabapples.system.dao.jpa.RolesRepository;
import cn.crabapples.system.dao.mybatis.MenusMapper;
import cn.crabapples.system.entity.SysMenus;
import cn.crabapples.system.entity.SysRoleMenus;
import cn.crabapples.system.entity.SysRoles;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TODO 系统菜单DAO
 *
 * @author Mr.He
 * 2021/4/12 18:08
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Component
public class MenusDAO extends BaseDAO<SysMenus, String> {
    private final MenusRepository repository;
    private final RolesRepository rolesRepository;
    private final MenusMapper mapper;

    public MenusDAO(MenusRepository repository, RolesRepository rolesRepository, MenusMapper mapper) {
        this.repository = repository;
        this.rolesRepository = rolesRepository;
        this.mapper = mapper;
    }

    public long count() {
        return repository.countByDelFlagAndIsRoot(DIC.NOT_DEL, DIC.IS_ROOT);
    }

    public SysMenus findById(String id) {
        Optional<SysMenus> optional = repository.findByDelFlagAndId(DIC.NOT_DEL, id);
        return checkOptional(optional);
    }


    public SysMenus save(SysMenus entity) {
        return repository.save(entity);
    }

    public Page<SysMenus> findMenusTree(PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_SORT);
        return repository.findByDelFlagAndIsRoot(pageable, DIC.NOT_DEL, DIC.IS_ROOT);
    }

    public List<SysMenus> findMenusTree() {
        SysMenus sysMenusExample = new SysMenus();
        sysMenusExample.setIsRoot(DIC.IS_ROOT);
        sysMenusExample.setDelFlag(DIC.NOT_DEL);
        ExampleMatcher matcher = ExampleMatcher.matching()
                //对 isRoot 字段精确匹配
                .withMatcher("isRoot", ExampleMatcher.GenericPropertyMatchers.exact())
                //对 delFlag 字段精确匹配
                .withMatcher("delFlag", ExampleMatcher.GenericPropertyMatchers.exact());
        Example<SysMenus> example = Example.of(sysMenusExample, matcher);
        return repository.findAll(example);
    }

    public List<SysMenus> findByParentId(String id) {
        throw new ApplicationException("暂未实现");
    }

    public List<SysMenus> findByIds(List<String> ids) {
        return repository.findByDelFlagAndIdIn(DIC.NOT_DEL, ids);
    }

    public List<SysMenus> findButtonsByIds(List<String> ids) {
        System.err.println(ids);
        Specification<SysMenus> specification = new Specification<SysMenus>() {
            final List<Predicate> predicates = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root<SysMenus> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                predicates.add(builder.equal(root.get("menusType"), DIC.MENUS_TYPE_BUTTON));
                predicates.add(builder.equal(root.get("delFlag"), DIC.NOT_DEL));
                predicates.add(builder.in(root.get("id")).value(ids));
                return query.where(predicates.toArray(new Predicate[0])).getRestriction();
            }
        };
        return repository.findAll(specification);
    }
    public List<SysMenus> findButtonsByIds1(List<String> ids) {
       return mapper.findButtonsByIds(ids);
    }

    public void remove(String id) {
        repository.deleteById(id);
    }

    public SysRoleMenus getRoleMenus(String id) {
        SysRoles role = rolesRepository.findById(id).orElseThrow(() -> new ApplicationException("角色不存在"));
        List<SysMenus> menusList = repository.findByDelFlagAndIsRootAndIdIn(DIC.NOT_DEL, DIC.NOT_DEL, role.getMenusIds());
//        List<SysMenus> menusList = repository.findByIds(role.getMenusIds());
        return new SysRoleMenus(role, menusList);
    }
}
