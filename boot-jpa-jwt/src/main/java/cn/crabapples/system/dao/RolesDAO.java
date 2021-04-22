package cn.crabapples.system.dao;

import cn.crabapples.common.DIC;
import cn.crabapples.common.PageDTO;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.jpa.RolesRepository;
import cn.crabapples.system.entity.SysRoles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
public class RolesDAO extends BaseDAO {
    private final RolesRepository rolesRepository;

    public RolesDAO(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public long count() {
        return rolesRepository.count(DIC.NOT_DEL);
    }

    public SysRoles findById(String id) {
        Optional<SysRoles> optional = rolesRepository.findByDelFlagAndId(DIC.NOT_DEL, id);
        return checkOptional(optional);
    }


    public SysRoles save(SysRoles entity) {
        return rolesRepository.save(entity);
    }

    public Page<SysRoles> findAll(PageDTO page) {
        Pageable pageable = PageRequest.of(page.getPageIndex(), page.getPageSize(), ASC_CREATE_TIME);
        return rolesRepository.findByDelFlag(pageable, DIC.NOT_DEL);
    }

    public List<SysRoles> findByIds(List<String> ids) {
        return rolesRepository.findByDelFlagAndIdIn(DIC.NOT_DEL, ids);
    }

    public List<SysRoles> findByMenusId(String menusId) {
        return rolesRepository.findByDelFlagAndMenusIdsContains(DIC.NOT_DEL, menusId);
    }


}
