package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.system.dao.jpa.RolesRepository;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.form.RolesForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

/**
 * TODO 系统角色DAO
 *
 * @author Mr.He
 * 2021/4/13 4:05
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Component
public class RolesDAO extends BaseDAO<SysRoles, String> {
    private final RolesRepository repository;

    public RolesDAO(RolesRepository repository) {
        this.repository = repository;
    }

    public long count() {
        return repository.count();
    }

    public SysRoles findById(String id) {
        Optional<SysRoles> optional = repository.findById(id);
        return checkOptional(optional);
    }

    public SysRoles save(SysRoles entity) {
        return repository.save(entity);
    }

    private Specification<SysRoles> initQueryParams(RolesForm form) {
        return new Specification<SysRoles>() {
            final List<Predicate> predicates = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root root, @NonNull CriteriaQuery query, CriteriaBuilder builder) {
                predicates.add(builder.equal(root.get("delFlag"), DIC.NOT_DEL));
                if (Objects.nonNull(form)) {
                    if (Objects.nonNull(form.getName())) {
                        predicates.add(builder.equal(root.get("name"), form.getName()));
                    }
                    if (Objects.nonNull(form.getId())) {
                        predicates.add(builder.equal(root.get("id"), form.getId()));
                    }
                }
                Predicate[] params = predicates.toArray(new Predicate[0]);
                return query.where(params).getRestriction();
            }
        };

    }

    public Iterable<SysRoles> findAll(Integer pageIndex, Integer pageSize, RolesForm form) {
        Specification<SysRoles> specification = initQueryParams(form);
        if (null == pageIndex) {
            return repository.findAll(specification);
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize, DESC_CREATE_TIME);
        return repository.findAll(specification, pageable);
    }

    public List<SysRoles> findByIds(List<String> ids) {
        Specification<SysRoles> specification = new Specification<SysRoles>() {
            final List<Predicate> predicates = new ArrayList<>();

            @Override
            public Predicate toPredicate(Root root, @NonNull CriteriaQuery query, CriteriaBuilder builder) {
                predicates.add(builder.equal(root.get("delFlag"), DIC.NOT_DEL));
                predicates.add(builder.in(root.get("id")).value(ids));
                Predicate[] params = predicates.toArray(new Predicate[0]);
                return query.where(params).getRestriction();
            }
        };
        return repository.findAll(specification);
    }

    public List<SysRoles> findByIds(String[] ids) {
        return repository.findByIds(Arrays.asList(ids));
    }


    public List<SysRoles> findByMenusId(String menusId) {
        throw new ApplicationException("暂未实现");
//        return repository.findByDelFlagAndMenusIdsContains(DIC.NOT_DEL, menusId);
    }


}
