package cn.crabapples.system.dao;

import cn.crabapples.common.DIC;
import cn.crabapples.system.dao.mybatis.MenusMapper;
import cn.crabapples.system.dao.mybatis.RolesMapper;
import cn.crabapples.system.entity.SysMenus;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
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
public class MenusDAO extends ServiceImpl<MenusMapper, SysMenus> {
    private final RolesMapper rolesMapper;

    public MenusDAO(RolesMapper rolesMapper) {
        this.rolesMapper = rolesMapper;
    }

    public SysMenus findById(String id) {
        return mapper.selectOneById(id);
    }

    public boolean save(SysMenus entity) {
        return saveOrUpdate(entity);
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
