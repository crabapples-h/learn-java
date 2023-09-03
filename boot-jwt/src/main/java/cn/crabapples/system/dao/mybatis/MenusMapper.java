package cn.crabapples.system.dao.mybatis;

import cn.crabapples.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenusMapper extends BaseMapper<SysMenu> {


    List<SysMenu> getUserMenus(@Param("userId") String id);

    List<SysMenu> findMenusTree(@Param("id") String id);

}
