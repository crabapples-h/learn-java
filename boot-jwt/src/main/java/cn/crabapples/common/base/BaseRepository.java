package cn.crabapples.common.base;


import cn.crabapples.common.DIC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * TODO JAP接口基础方法-对部分查询方法进行查询或添加
 *
 * @author Mr.He
 * 2021/4/12 20:04
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity_Jpa, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    /**
     * 重写jpa的count方法
     */
    @Override
    default long count() {
        return countByDelFlag(DIC.NOT_DEL);
    }

    default List<T> findByIds(List<ID> ids) {
        return findByDelFlagAndIdIn(DIC.NOT_DEL, ids);
    }

    /**
     * 拓展jpa的findByIds方法使其支持数组
     */
    default List<T> findByIds(ID[] ids) {
        List<ID> list = Arrays.stream(ids).collect(Collectors.toList());
        return findByIds(list);
    }

    /**
     * 重写jpa的count方法
     */
    @Override
    default Optional<T> findById(ID id) {
        return findByDelFlagAndId(DIC.NOT_DEL, id);
    }

    @Override
    default List<T> findAll() {
        return findByDelFlag(DIC.NOT_DEL);
    }

    @Override
    default Page<T> findAll(Pageable pageable) {
        return findByDelFlag(pageable, DIC.NOT_DEL);
    }

    default void deleteLogicById(ID id) {
        Optional<T> optional = findById(id);
        optional.ifPresent(e -> {
            e.setDelFlag(DIC.IS_DEL);
            saveAndFlush(e);
        });
    }

    List<T> findByDelFlagAndIdIn(Integer delFlag, List<ID> ids);

    Optional<T> findByDelFlagAndId(Integer delFlag, ID id);

    List<T> findByDelFlag(Integer delFlag);


    Page<T> findByDelFlag(Pageable pageable, Integer delFlag);


    long countByDelFlag(Integer delFlag);

}
