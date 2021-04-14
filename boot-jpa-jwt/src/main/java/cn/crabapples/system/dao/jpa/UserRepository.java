package cn.crabapples.system.dao.jpa;

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
public interface UserRepository extends JpaRepository<SysUser, String>,BaseRepository<SysUser> {
    Optional<SysUser> findByDelFlagAndUsername(int delFlag, String username);

    Optional<SysUser> findByDelFlagAndIdAndStatus(int delFlag, String id, int status);

    List<SysUser> findByDelFlagAndName(int delFlag, String name);

    List<SysUser> findByDelFlagAndNameLike(int delFlag, String name);

    List<SysUser> findByDelFlag(int delFlag);


    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    @Query("update SysUser set delFlag = 1 where id=:name")
    @Modifying
    void delUser(@Param("name") String id);

    /**
     * 禁用用户
     *
     * @param id 用户ID
     */
    @Query("update SysUser set status = 1 where id = :id")
    @Modifying
    void unActiveUser(@Param("id") String id);

    /**
     * 激活用户
     *
     * @param id 用户ID
     */
    @Query("update SysUser set status = 0 where id = :id")
    @Modifying
    void activeUser(@Param("id") String id);



}
