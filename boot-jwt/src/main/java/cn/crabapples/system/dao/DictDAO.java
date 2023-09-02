package cn.crabapples.system.dao;

import cn.crabapples.system.dao.mybatis.DictMapper;
import cn.crabapples.system.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class DictDAO extends ServiceImpl<DictMapper, SysDict> {
}
