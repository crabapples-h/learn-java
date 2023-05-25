package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.jpa.DictRepository;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.DictForm;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DictDAO extends BaseDAO {
    private final DictRepository repository;

    public DictDAO(DictRepository repository) {
        this.repository = repository;
    }

    public Iterable<SysDict> queryList(DictForm form, Pageable page) {
        Specification<SysDict> specification = (root, query, builder) -> {
            final List<Predicate> predicateList = new ArrayList<>();
            if (Objects.nonNull(form)) {
                if (!StringUtils.isEmpty(form.getName())) {
                    String value = MessageFormat.format("%{0}%", form.getName());
                    predicateList.add(builder.equal(root.get("name"), value));
                }
                if (!StringUtils.isEmpty(form.getCode())) {
                    String value = MessageFormat.format("%{0}%", form.getCode());
                    predicateList.add(builder.equal(root.get("code"), value));
                }
            }
            Predicate[] objects = predicateList.toArray(new Predicate[0]);
            return query.where(objects).getRestriction();
        };
        return repository.findAll(specification, page);
    }

    public void save(DictForm form) {
        SysDict entity = form.toEntity();
        repository.save(entity);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public SysDict getById(String id) {
        return repository.findById(id).orElseThrow(() -> new ApplicationException("数据不存在"));
    }
}
