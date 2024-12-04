package cn.crabapples.system.sysDict.dao;

import cn.crabapples.system.sysDict.dao.mybatis.mapper.DictItemMapper;
import cn.crabapples.system.sysDict.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class DictItemDAO extends ServiceImpl<DictItemMapper, SysDictItem> {
}
