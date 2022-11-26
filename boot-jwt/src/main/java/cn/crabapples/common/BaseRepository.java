package cn.crabapples.common;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

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
public interface BaseRepository<T, ID>  {
    default long count(int delFlag) {
        return countByDelFlag(delFlag);
    }

    long countByDelFlag(int delFlag);

    Optional<T> findByDelFlagAndId(int delFlag, ID id);

    List<T> findByDelFlagAndIdIn(int delFlag, List<ID> ids);

    List<T> findByDelFlagAndIdIn(int delFlag, ID[] ids);

    Page<T> findByDelFlag(Pageable pageable, int delFlag);

    List<T> findByDelFlag(int delFlag);

}
