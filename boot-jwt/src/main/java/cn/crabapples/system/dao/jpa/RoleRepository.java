package cn.crabapples.system.dao.jpa;

import cn.crabapples.common.base.BaseRepository;
import cn.crabapples.system.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


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
public interface RoleRepository extends JpaRepository<SysRole, String> ,BaseRepository<SysRole,String>{

//    List<SysRole> findByDelFlagAndMenusIdsContains(int delFlag, String menusId);


}
