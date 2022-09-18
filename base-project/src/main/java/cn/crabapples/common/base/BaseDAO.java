package cn.crabapples.common.base;

import cn.crabapples.common.ApplicationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * TODO 数据库查询基本字段
 *
 * @author Mr.He
 * 2021/4/12 3:27
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public abstract class BaseDAO {
    protected final static Sort DESC_CREATE_TIME = Sort.by("createTime").descending();
    protected final static Sort ASC_CREATE_TIME = Sort.by("createTime").ascending();
    protected final static Sort ASC_SORT = Sort.by("sort").ascending();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    protected <T> T checkOptional(@NotNull Optional<T> optional) {
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ApplicationException("找不到对应数据");
    }

    protected <T, ID> T findById(JpaRepository<T, ID> repository, ID id) {
        Optional<T> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ApplicationException("找不到对应数据");
    }

    protected <T, ID> List<T> findAll(JpaRepository<T, ID> repository) {
        return repository.findAll();
    }

    protected <T, ID> Page<T> findAll(JpaRepository<T, ID> repository, Pageable page) {
        return repository.findAll(page);
    }

    public <T, ID> void remove(JpaRepository<T, ID> repository, ID id) {
        repository.deleteById(id);
    }
}
