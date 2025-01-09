package cn.crabapples.system.sysRole.dao;

import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.sysRole.dao.mybatis.mapper.RolesMapper;
import cn.crabapples.system.sysRole.entity.SysRole;
import cn.crabapples.system.sysRole.form.RolesForm;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO 系统角色DAO
 *
 * @author Mr.He
 * 2021/4/13 4:05
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Component
public class RolesDAO extends ServiceImpl<RolesMapper, SysRole> {

    @Transactional
    public boolean save(SysRole entity) {
        return saveOrUpdate(entity);
    }

    public Page<SysRole> findAll(Integer pageIndex, Integer pageSize, RolesForm form) {
        Page<SysRole> page = Page.of(pageIndex, pageSize);
        QueryWrapper wrapper = QueryWrapper.create(form.toEntity());
        return mapper.paginate(page, wrapper);
    }

    public List<SysRole> findAll(RolesForm form) {
        QueryWrapper wrapper = QueryWrapper.create(form.toEntity());
        return mapper.selectListByQuery(wrapper);
    }

    public List<SysRole> findByIds(List<String> ids) {
        return mapper.selectListByIds(ids);
    }


    public boolean deleteById(String id) {
        return removeById(id);
    }

    public List<SysRolesDTO> getUserRolesDTO(String id) {
        return getUserRoles(id).stream().map(e -> {
            SysRolesDTO dto = new SysRolesDTO();
            BeanUtils.copyProperties(e, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<SysRole> getUserRoles(String id) {
        return mapper.getUserRoles(id);
    }
}
