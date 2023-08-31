package cn.crabapples.system.dao.jpa;

import cn.crabapples.common.base.BaseRepository;
import cn.crabapples.system.entity.SysMenus;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO 系统菜单持久层
 *
 * @author Mr.He
 * 3/2/20 12:15 AM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Repository
public interface MenusRepository extends BaseRepository<SysMenus, String> {

    long countByDelFlagAndIsRoot(int delFlag, int isRoot);

    Page<SysMenus> findByDelFlagAndIsRoot(Pageable pageable, int delFlag, int isRoot);

    List<SysMenus> findByDelFlagAndIsRoot(int delFlag, int isRoot);

    List<SysMenus> findByDelFlagAndIsRootAndIdIn(int delFlag, int isRoot, List<String> ids);

    List<SysMenus> findByDelFlagAndIdInAndMenusType(int delFlag, List<String> ids, int menusType);

}
