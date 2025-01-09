package cn.crabapples.system.sysUser.dao;

import cn.crabapples.common.dic.DIC;
import cn.crabapples.system.dto.SysUserDTO;
import cn.crabapples.system.sysUser.dao.mybatis.mapper.UserMapper;
import cn.crabapples.system.sysUser.entity.SysUser;
import cn.crabapples.system.sysUser.form.UserForm;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDAO extends ServiceImpl<UserMapper, SysUser> {

    public Page<SysUserDTO> findAll(Integer pageIndex, Integer pageSize, UserForm form) {
        Page<SysUser> page = Page.of(pageIndex, pageSize);
        QueryWrapper wrapper = QueryWrapper.create(SysUser.class)
                .like(SysUser::getUsername, form.getUsername(), !StringUtils.isEmpty(form.getUsername()))
                .like(SysUser::getName, form.getName(), !StringUtils.isEmpty(form.getName()))
                .like(SysUser::getMail, form.getMail(), !StringUtils.isEmpty(form.getMail()))
                .like(SysUser::getPhone, form.getPhone(), !StringUtils.isEmpty(form.getPhone()));
        Page<SysUser> sysUserList = mapper.paginate(page, wrapper);
        List<SysUserDTO> collect = sysUserList.getRecords().stream().map(e -> {
            SysUserDTO dto = new SysUserDTO();
            BeanUtils.copyProperties(e, dto);
            return dto;
        }).collect(Collectors.toList());
        Page<SysUserDTO> dtoPage = new Page<>();
        BeanUtils.copyProperties(sysUserList, dtoPage);
        dtoPage.setRecords(collect);
        return dtoPage;
    }

    public List<SysUserDTO> findAll(UserForm form) {
        return mapper.selectListByQuery(
                QueryWrapper.create(SysUser.class)
                        .like(SysUser::getUsername, form.getUsername(), !StringUtils.isEmpty(form.getUsername()))
                        .like(SysUser::getName, form.getName(), !StringUtils.isEmpty(form.getName()))
                        .like(SysUser::getMail, form.getMail(), !StringUtils.isEmpty(form.getMail()))
                        .like(SysUser::getPhone, form.getPhone(), !StringUtils.isEmpty(form.getPhone()))).stream().map(e -> {
            SysUserDTO dto = new SysUserDTO();
            BeanUtils.copyProperties(e, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public SysUser findOne(UserForm form) {
        return getOne( QueryWrapper.create(form.toEntity()));
    }

    public SysUser findById(String id) {
        return mapper.selectOneById(id);
    }

    public List<SysUser> findByIds(List<String> ids) {
        return mapper.selectListByIds(ids);
    }

    public boolean removeUser(String id) {
        return removeById(id);
    }

    public boolean lockUser(String id) {
        return updateById(SysUser.create().setId(id).setStatus(DIC.USER_LOCK));
    }

    public boolean unlockUser(String id) {
        return updateById(SysUser.create().setId(id).setStatus(DIC.USER_UNLOCK));
    }
}
