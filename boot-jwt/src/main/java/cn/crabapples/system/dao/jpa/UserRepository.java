package cn.crabapples.system.dao.jpa;

import cn.crabapples.common.base.BaseRepository;
import cn.crabapples.system.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * TODO 系统用户持久层
 *
 * @author Mr.He
 * 2019/7/4 1422:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
@Repository
public interface UserRepository extends JpaRepository<SysUser, String> ,BaseRepository<SysUser,String>{
    Optional<SysUser> findByDelFlagAndUsername(int delFlag, String username);

    Optional<SysUser> findByDelFlagAndIdAndStatus(int delFlag, String id, int status);

    List<SysUser> findByDelFlagAndNameContaining(int delFlag, String name);

    List<SysUser> findByDelFlag(int delFlag);

    @Query("update SysUser set delFlag = :delFlag where id=:id")
    @Modifying
    void delUser(@Param("id") String id, @Param("delFlag") int delFlag);

    @Query("update SysUser set status = :status where id = :id")
    @Modifying
    void lockUnlockUser(@Param("id") String id, @Param("status") int status);


}
