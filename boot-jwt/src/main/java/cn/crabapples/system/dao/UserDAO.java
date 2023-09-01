package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.common.DIC;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.mybatis.UserMapper;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO extends BaseDAO<SysUser, String> {
    private final UserMapper mapper;

    public UserDAO(UserMapper mapper) {
        this.mapper = mapper;
    }


    public List<SysUser> findAll() {
        return mapper.selectAll();
    }

    public List<SysUser> findAll(UserForm form) {
        return mapper.selectListByQuery(QueryWrapper.create(form.toEntity()));
    }

    public SysUser findOne(UserForm form) {
        SysUser example = form.toEntity();
        System.err.println(example);
        example.setDelFlag(DIC.NOT_DEL);
        QueryWrapper wrapper = QueryWrapper.create(example);
        System.err.println(wrapper);
        return mapper.selectOneByQuery(wrapper);
    }

    public SysUser findById(String id) {
        return mapper.selectOneById(id);
    }

    public List<SysUser> findByIds(List<String> ids) {
        return mapper.selectListByIds(ids);
    }

    public int save(SysUser user) {
        return mapper.insertOrUpdate(user);
    }

    public int delUser(String id) {
        return mapper.deleteById(id);
    }

    public int lockUser(String id) {
        throw new ApplicationException("暂未实现");
    }

    public int unlockUser(String id) {
        throw new ApplicationException("暂未实现");
    }
}
