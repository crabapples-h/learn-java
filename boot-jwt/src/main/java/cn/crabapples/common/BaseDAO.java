package cn.crabapples.common;

import cn.crabapples.system.entity.SysUser;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
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
public abstract class BaseDAO<T, ID> {
    protected final static Sort DESC_CREATE_TIME = Sort.by("createTime").descending();
    protected final static Sort ASC_CREATE_TIME = Sort.by("createTime").ascending();
    protected final static Sort ASC_SORT = Sort.by("sort").ascending();

    /**
     * 检查是否查询到数据
     *
     * @param optional 查询结果包装
     * @return 查询结果
     */
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    protected final T checkOptional(@NotNull Optional<T> optional) {
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ApplicationException("找不到对应数据");
    }

    /**
     * 根据id查找
     *
     * @param repository jpa持久化工具
     * @param id         id
     * @return 查询结果
     */
    protected final T findById(BaseRepository<T, ID> repository, ID id) {
        Optional<T> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ApplicationException("找不到对应数据");
    }

    /**
     * 查询所有数据
     *
     * @param repository jpa持久化工具
     * @return 查询结果
     */
    final protected List<T> findAll(BaseRepository<T, ID> repository) {
        return repository.findAll();
    }

    protected final List<T> findAll(BaseRepository<T, ID> repository, Example<T> example) {
        return repository.findAll(example);
    }

    protected final List<T> findAll(BaseRepository<T, ID> repository, Specification<T> specification) {
        return repository.findAll(specification);
    }

    /**
     * 查询所有数据
     *
     * @param repository jpa持久化工具
     * @param page       分页参数
     * @return 查询结果
     */
    protected final Page<T> findAll(BaseRepository<T, ID> repository, Pageable page) {
        return repository.findAll(page);
    }

    /**
     * 根据id删除
     *
     * @param repository jpa持久化工具
     * @param id         id
     */
    protected final void remove(BaseRepository<T, ID> repository, ID id) {
        repository.deleteById(id);
    }

    /**
     * 查询数据条数
     *
     * @param repository jpa持久化工具
     * @return 数据条数
     */
    protected final long count(BaseRepository<T, ID> repository) {
        return repository.count();
    }

    /**
     * 根据条件查询数据条数
     *
     * @param repository jpa持久化工具
     * @param example    查询条件
     * @return 数据条数
     */
    protected final long count(BaseRepository<T, ID> repository, Example<T> example) {
        return repository.count(example);
    }

}
