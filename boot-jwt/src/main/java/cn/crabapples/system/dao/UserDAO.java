package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.system.dao.jpa.UserRepository;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserDAO extends BaseDAO {
    private final UserRepository repository;

    public UserDAO(UserRepository repository) {
        this.repository = repository;
    }

    public long count() {
        return repository.countByDelFlag(DIC.NOT_DEL);
    }

    public SysUser findById(String id) {
        Optional<SysUser> optional = repository.findByDelFlagAndId(DIC.NOT_DEL, id);
        return optional.orElseThrow(() -> new ApplicationException("找不到对应数据"));
    }

    public List<SysUser> findByIds(List<String> ids) {
        Specification<SysUser> specification = (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(builder.in(root.get("id")).value(ids));
            Predicate[] predicates = predicateList.toArray(new Predicate[0]);
            return query.where(predicates).getRestriction();
        };
        return repository.findAll(specification);
    }


    public SysUser save(SysUser user) {
        return repository.save(user);
    }

    public void delUser(String id) {
        repository.delUser(id, DIC.IS_DEL);
    }

    public void lockUser(String id) {
        repository.lockUnlockUser(id, DIC.USER_LOCK);
    }

    public void unlockUser(String id) {
        repository.lockUnlockUser(id, DIC.USER_UNLOCK);
    }

    public List<SysUser> getList(UserForm form) {
        Specification<SysUser> specification = createQueryParam(form);
        return repository.findAll(specification);
    }

    public Page<SysUser> getList(UserForm form, PageRequest page) {
        Specification<SysUser> specification = createQueryParam(form);
        return repository.findAll(specification, page);
    }

    private Specification<SysUser> createQueryParam(UserForm form) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (Objects.nonNull(form)) {
                if (!StringUtils.isEmpty(form.getUsername())) {
                    String value = MessageFormat.format("%{0}%", form.getUsername());
                    predicateList.add(builder.like(root.get("username"), value));
                }
                if (!StringUtils.isEmpty(form.getName())) {
                    String value = MessageFormat.format("%{0}%", form.getName());
                    predicateList.add(builder.like(root.get("name"), value));
                }
                if (!StringUtils.isEmpty(form.getId())) {
                    String value = MessageFormat.format("{0}", form.getId());
                    predicateList.add(builder.like(root.get("id"), value));
                }
                if (Objects.nonNull(form.getDelFlag())) {
                    String value = MessageFormat.format("{0}", form.getDelFlag());
                    predicateList.add(builder.like(root.get("delFlag"), value));
                }
            }
            Predicate[] predicates = predicateList.toArray(new Predicate[0]);
            return query.where(predicates).getRestriction();
        };
    }

    public SysUser findOne(UserForm form) {
        Specification<SysUser> specification = createQueryParam(form);
        return repository.findOne(specification).orElseThrow(() -> new ApplicationException("用户不存在"));
    }
}
