package cn.crabapples.system.dao.jpa;

import cn.crabapples.common.base.BaseRepository;
import cn.crabapples.system.entity.SysDict;
import cn.crabapples.system.entity.SysDictItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * TODO 系统字典项持久层
 *
 * @author Ms.He
 * 2023/5/18 20:19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Repository
public interface DictItemRepository extends JpaRepository<SysDictItem, String>, BaseRepository<SysDictItem, String> {

}
