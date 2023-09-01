package cn.crabapples.system.dao;

import cn.crabapples.common.ApplicationException;
import cn.crabapples.system.dao.mybatis.RolesMapper;
import cn.crabapples.system.dto.SysRolesDTO;
import cn.crabapples.system.entity.SysRoles;
import cn.crabapples.system.form.RolesForm;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

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
public class RolesDAO extends ServiceImpl<RolesMapper, SysRoles> {

    public SysRoles findById(String id) {
        return mapper.selectOneById(id);
    }

    public boolean save(SysRoles entity) {
        return saveOrUpdate(entity);
    }

    public List<SysRoles> findAll(Integer pageIndex, Integer pageSize, RolesForm form) {
        QueryWrapper wrapper = QueryWrapper.create(form.toEntity());
        return mapper.selectListByQuery(wrapper);
    }

    public List<SysRoles> findByIds(List<String> ids) {
        return mapper.selectListByIds(ids);
    }

    public List<SysRoles> findByMenusId(String menusId) {
        throw new ApplicationException("暂未实现");
//        return repository.findByDelFlagAndMenusIdsContains(DIC.NOT_DEL, menusId);
    }


    public boolean deleteById(String id) {
        return removeById(id);
    }

    public List<SysRolesDTO> getUserRoles(String id) {
        return mapper.getUserRoles(id);
    }
}
