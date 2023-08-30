package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.system.dao.jpa.MenusRepository;
import cn.crabapples.system.entity.SysMenus;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

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

    public MenusDAO(MenusRepository repository) {
        this.repository = repository;
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

    public List<SysMenus> findRootsByIds(List<String> ids) {
        return repository.findByDelFlagAndIsRootAndIdIn(DIC.NOT_DEL, DIC.IS_ROOT, ids);
    }

    public List<SysMenus> findButtonsByIds(List<String> ids) {
        return repository.findByDelFlagAndIdInAndMenusType(DIC.NOT_DEL, ids, DIC.MENUS_TYPE_BUTTON);
    }

    public void remove(String id) {
        repository.deleteById(id);
    }
}
