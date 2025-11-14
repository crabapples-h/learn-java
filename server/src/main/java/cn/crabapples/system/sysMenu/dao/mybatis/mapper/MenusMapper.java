package cn.crabapples.system.sysMenu.dao.mybatis.mapper;

import cn.crabapples.system.sysMenu.entity.SysMenu;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.paginate.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenusMapper extends BaseMapper<SysMenu> {
    List<SysMenu> getUserMenus(@Param("userId") String id);

    List<SysMenu> findMenusTree(@Param("id") String id);

    Page<SysMenu> findMenusTreePage(Page<SysMenu> page, @Param("id") String id);

    Page<SysMenu> findMenusListPage(Page<SysMenu> page);

    List<SysMenu> findMenusList(@Param("id")String pid);

}
