package cn.crabapples.common.base;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * TODO JAP接口基础方法
 *
 * @author Mr.He
 * 2021/4/12 20:04
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    default long count(Integer delFlag) {
        return countByDelFlag(delFlag);
    }

    long countByDelFlag(Integer delFlag);

    Optional<T> findByDelFlagAndId(Integer delFlag, ID id);

    List<T> findByDelFlagAndIdIn(Integer delFlag, List<ID> ids);

    List<T> findByDelFlagAndIdIn(Integer delFlag, ID[] ids);

    Page<T> findByDelFlag(Pageable pageable, Integer delFlag);

    List<T> findByDelFlag(Integer delFlag);

}
