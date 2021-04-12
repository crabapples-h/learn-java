package cn.crabapples.system.dao.jpa;

import cn.crabapples.system.entity.SysMenus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
public interface MenusRepository extends JpaRepository<SysMenus, String> {

    long countByDelFlagAndIsRoot(int delFlag, int isRoot);

    Optional<SysMenus> findByDelFlagAndId(int notDel, String id);

    Page<SysMenus> findByDelFlag(Pageable pageable, int notDel);

    Page<SysMenus> findByDelFlagAndIsRoot(Pageable pageable, int delFlag, int isRoot);
}
