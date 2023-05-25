package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.common.DIC;
import cn.crabapples.system.dao.jpa.MenusRepository;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.form.MenusForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.text.MessageFormat;
import java.util.*;

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
public class MenusDAO extends BaseDAO {
    private final MenusRepository repository;

    public MenusDAO(MenusRepository repository) {
        this.repository = repository;
    }

    public long count(MenusForm form) {
        Specification<SysMenu> specification = createQueryParam(form);
        return repository.count(specification);
    }

    public List<SysMenu> queryList(MenusForm form) {
        Specification<SysMenu> specification = createQueryParam(form);
        return repository.findAll(specification);
    }

    public Page<SysMenu> queryList(MenusForm form, Pageable page) {
        Specification<SysMenu> specification = createQueryParam(form);
        return repository.findAll(specification,page);
    }


    private Specification<SysMenu> createQueryParam(MenusForm form) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (Objects.nonNull(form)) {
                if (!StringUtils.isEmpty(form.getName())) {
                    String value = MessageFormat.format("%{0}%", form.getName());
                    predicateList.add(builder.like(root.get("name"), value));
                }
                if (Objects.nonNull(form.getMenusType())) {
                    String value = MessageFormat.format("{0}", form.getMenusType());
                    predicateList.add(builder.equal(root.get("menusType"), value));
                }
//                if (Objects.nonNull(form.getIsRoot())) {
//                    String value = MessageFormat.format("{0}", form.getIsRoot());
//                    predicateList.add(builder.equal(root.get("isRoot"), value));
//                }
                if (Objects.nonNull(form.getDelFlag())) {
                    String value = MessageFormat.format("{0}", form.getDelFlag());
                    predicateList.add(builder.equal(root.get("delFlag"), value));
                }
            }
            Predicate[] predicatesArray = predicateList.toArray(new Predicate[0]);
            return query.where(predicatesArray).getRestriction();
        };
    }

    public SysMenu findById(String id) {
        Optional<SysMenu> optional = repository.findByDelFlagAndId(DIC.NOT_DEL, id);
        return optional.orElseThrow(() -> new ApplicationException("找不到对应数据"));
    }



    public SysMenu save(SysMenu entity) {
        return repository.save(entity);
    }

    public List<SysMenu> findByIds(List<String> ids) {
        return repository.findByDelFlagAndIdIn(DIC.NOT_DEL, ids);
    }

    public List<SysMenu> findButtonsByIds(List<String> ids) {
        return repository.findByDelFlagAndIdInAndMenusType(DIC.NOT_DEL, ids, DIC.MENUS_TYPE_BUTTON);
    }

    public void remove(String id) {
        repository.deleteById(id);
    }
}
