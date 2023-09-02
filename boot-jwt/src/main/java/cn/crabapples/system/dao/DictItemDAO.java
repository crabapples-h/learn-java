package cn.crabapples.system.dao;

import cn.crabapples.system.dao.mybatis.DictItemMapper;
import cn.crabapples.system.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class DictItemDAO extends ServiceImpl<DictItemMapper, SysDictItem> {
}
