package cn.crabapples.system.dao;

import cn.crabapples.common.DIC;
import cn.crabapples.common.base.BaseDAO;
import cn.crabapples.system.dao.mybatis.MenusMapper;
import cn.crabapples.system.dao.mybatis.RolesMapper;
import cn.crabapples.system.entity.SysMenus;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO 系统菜单DAO
 *
 * @author Mr.He
 * 2021/4/12 18:08
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Component
public class MenusDAO extends BaseDAO<SysMenus, String> {
    private final MenusMapper mapper;
    private final RolesMapper rolesMapper;

    public MenusDAO(MenusMapper mapper, RolesMapper rolesMapper) {
        this.rolesMapper = rolesMapper;
        this.mapper = mapper;
    }

    public SysMenus findById(String id) {
        return mapper.selectOneById(id);
    }

    public int save(SysMenus entity) {
        return mapper.insertOrUpdate(entity);
    }

    public List<SysMenus> findMenusTree() {
        SysMenus example = new SysMenus();
        example.setIsRoot(DIC.IS_ROOT);
        example.setDelFlag(DIC.NOT_DEL);
        QueryWrapper wrapper = QueryWrapper.create(example);
        return mapper.selectListByQuery(wrapper);
    }

    public List<SysMenus> findButtonsByIds1(List<String> ids) {
        return mapper.findButtonsByIds(ids);
    }

    public void remove(String id) {
        mapper.deleteById(id);
    }

//    public SysRoleMenus getRoleMenus(String id) {
//        SysRoles role = rolesRepository.findById(id).orElseThrow(() -> new ApplicationException("角色不存在"));
//        List<SysMenus> menusList = repository.findByDelFlagAndIsRootAndIdIn(DIC.NOT_DEL, DIC.NOT_DEL, role.getMenusIds());
////        List<SysMenus> menusList = repository.findByIds(role.getMenusIds());
//        return new SysRoleMenus(role, menusList);
//    }
}
