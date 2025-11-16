package demo.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.mybatis.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
