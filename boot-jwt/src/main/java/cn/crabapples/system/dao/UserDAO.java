package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.system.dao.mybatis.UserMapper;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO extends ServiceImpl<UserMapper, SysUser> {

    public List<SysUser> findAll() {
        return baseMapper.selectList(new QueryWrapper<>());
    }

    public List<SysUser> findAll(UserForm form) {
        return baseMapper.selectList(new QueryWrapper<>(form.toEntity()));
    }

    public SysUser findOne(UserForm form) {
        return getOne(new QueryWrapper<>(form.toEntity()));
    }

    public SysUser findById(String id) {
        return getById(id);
    }

    public List<SysUser> findByIds(List<String> ids) {
        return baseMapper.selectBatchIds(ids);
    }

    public boolean delUser(String id) {
        return removeById(id);
    }

    public boolean lockUser(String id) {
        throw new ApplicationException("暂未实现");
    }

    public boolean unlockUser(String id) {
        throw new ApplicationException("暂未实现");
    }
}
