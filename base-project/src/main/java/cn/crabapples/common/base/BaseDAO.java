//package cn.crabapples.common.base;
//
//import cn.crabapples.common.ApplicationException;
//import com.sun.xml.bind.v2.model.core.ID;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import javax.validation.constraints.NotNull;
//import java.util.List;
//import java.util.Optional;
//
///**
// * TODO 数据库查询基本字段
// *
// * @author Mr.He
// * 2021/4/12 3:27
// * e-mail crabapples.cn@gmail.com
// * qq 294046317
// * pc-name mrhe
// */
//public abstract class BaseDAO<T, ID> {
//    protected final static Sort DESC_CREATE_TIME = Sort.by("createTime").descending();
//    protected final static Sort ASC_CREATE_TIME = Sort.by("createTime").ascending();
//    protected final static Sort ASC_SORT = Sort.by("sort").ascending();
//
//    /**
//     * 检查是否查询到数据
//     *
//     * @param optional 查询结果包装
//     * @return 查询结果
//     */
//    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
//    protected T checkOptional(@NotNull Optional<T> optional) {
//        if (optional.isPresent()) {
//            return optional.get();
//        }
//        throw new ApplicationException("找不到对应数据");
//    }
//
//    /**
//     * 根据id查找
//     *
//     * @param repository jpa持久化工具
//     * @param id         id
//     * @return 查询结果
//     */
//    protected T findById(JpaRepository<T, ID> repository, ID id) {
//        Optional<T> optional = repository.findById(id);
//        if (optional.isPresent()) {
//            return optional.get();
//        }
//        throw new ApplicationException("找不到对应数据");
//    }
//
//    /**
//     * 查询所有数据
//     *
//     * @param repository jpa持久化工具
//     * @return 查询结果
//     */
//    protected List<T> findAll(JpaRepository<T, ID> repository) {
//        return repository.findAll();
//    }
//
//    /**
//     * 查询所有数据
//     *
//     * @param repository jpa持久化工具
//     * @param page       分页参数
//     * @return 查询结果
//     */
//    protected Page<T> findAll(JpaRepository<T, ID> repository, Pageable page) {
//        return repository.findAll(page);
//    }
//
//    /**
//     * 根据id删除
//     *
//     * @param repository jpa持久化工具
//     * @param id         id
//     */
//    public void remove(JpaRepository<T, ID> repository, ID id) {
//        repository.deleteById(id);
//    }
//
//    /**
//     * 查询数据条数
//     *
//     * @param repository jpa持久化工具
//     * @return 数据条数
//     */
//    public long count(BaseRepository <T, ID> repository) {
//        return repository.count();
//    }
//
//}
