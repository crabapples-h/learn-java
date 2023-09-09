package cn.crabapples.system.dao;

import cn.crabapples.system.dao.mybatis.RolesMapper;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysRole;
import cn.crabapples.system.form.RolesForm;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    public IPage<SysRole> findAll(Integer pageIndex, Integer pageSize, RolesForm form) {
        Page<SysRole> page = Page.of(pageIndex, pageSize);
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>(form.toEntity());
        return baseMapper.selectPage(page, wrapper);
    }

    public List<SysRole> findAll(RolesForm form) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>(form.toEntity());
        return baseMapper.selectList(wrapper);
    }

    public List<SysRole> findByIds(List<String> ids) {
        return baseMapper.selectBatchIds(ids);
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
        return baseMapper.getUserRoles(id);
    }
}
