package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.system.dao.mybatis.UserMapper;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO extends ServiceImpl<UserMapper, SysUser> {

    public List<SysUser> findAll() {
        return mapper.selectAll();
    }

    public List<SysUser> findAll(UserForm form) {
        return mapper.selectListByQuery(QueryWrapper.create(form.toEntity()));
    }

    public SysUser findOne(UserForm form) {
        SysUser example = form.toEntity();
        System.err.println(example);
        QueryWrapper wrapper = QueryWrapper.create(example);
        System.err.println(wrapper.toSQL());
        return mapper.selectOneByQuery(wrapper);
    }

    public SysUser findById(String id) {
        return mapper.selectOneById(id);
    }

    public List<SysUser> findByIds(List<String> ids) {
        return mapper.selectListByIds(ids);
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
