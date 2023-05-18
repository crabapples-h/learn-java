package cn.crabapples.system.dao.jpa;

import cn.crabapples.common.base.BaseRepository;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * TODO 系统字典持久层
 *
 * @author Ms.He
 * 2023/5/18 20:19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Repository
public interface DictRepository extends JpaRepository<SysDict, String>, BaseRepository<SysDict, String> {

}
