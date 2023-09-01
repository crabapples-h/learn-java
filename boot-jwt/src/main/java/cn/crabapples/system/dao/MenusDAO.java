package cn.crabapples.system.dao;

import cn.crabapples.common.DIC;
import cn.crabapples.system.dao.mybatis.MenusMapper;
import cn.crabapples.system.dao.mybatis.RolesMapper;
import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysRoleMenus;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class MenusDAO extends ServiceImpl<MenusMapper, SysMenu> {
    private final RolesMapper rolesMapper;

    public MenusDAO(RolesMapper rolesMapper) {
        this.rolesMapper = rolesMapper;
    }

    public SysMenu findById(String id) {
        return SysMenu.create().setId(id).oneById();
    }

    public boolean save(SysMenu entity) {
        return saveOrUpdate(entity);
    }

    public boolean remove(String id) {
        return SysMenu.create().setId(id).removeById();
    }

    public List<SysMenu> findButtonsByIds1(List<String> ids) {
        QueryWrapper wrapper = QueryWrapper.create()
                .where(SysMenu::getId).in(ids)
                .where(SysMenu::getMenusType).eq(DIC.MENUS_TYPE_BUTTON);
        return list(wrapper);
    }


    public List<SysMenu> getUserMenus(String id) {
        return mapper.getUserMenus(id);
    }

    /**
     * 一对多递归查询所有菜单树
     *
     * @return 菜单树
     */
    public List<SysMenu> findMenusTree() {
        QueryWrapper wrapper = QueryWrapper.create()
                .where(SysMenu::getPid).isNull(true);
        return mapper.selectListWithRelationsByQuery(wrapper);
    }

    /**
     * 根据获取角色菜单树
     *
     * @param id 角色id
     * @return 角色拥有的菜单树
     */
    public SysRoleMenus getRoleMenus(String id) {
        SysRoleMenus sysRoleMenus = mapper.getRoleMenus(id);
        List<SysMenu> tree = convertToTree(sysRoleMenus.getSysMenus());
        sysRoleMenus.setSysMenus(tree);
        return sysRoleMenus;
    }

    private List<SysMenu> convertToTree(List<SysMenu> menuList) {
        List<SysMenu> rootMenus = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menu.getPid() == null) {
                // 如果父节点ID为null，将此菜单视为根节点
                rootMenus.add(menu);
            } else {
                // 否则，寻找父节点，并将当前节点添加为其子节点
                addChildToParent(menuList, menu);
            }
        }
        return rootMenus;
    }

    private void addChildToParent(List<SysMenu> menuList, SysMenu childMenu) {
        for (SysMenu parentMenu : menuList) {
            if (parentMenu.getId().equals(childMenu.getPid())) {
                // 找到了父节点，将当前节点添加为其子节点
                List<SysMenu> children = parentMenu.getChildren();
                if (Objects.isNull(children)) {
                    children = new ArrayList<>();
                }
                children.add(childMenu);
                parentMenu.setChildren(children);
            } else {
                // 如果当前节点不是父节点，继续在子节点中查找
                List<SysMenu> children = parentMenu.getChildren();
                if (Objects.nonNull(children)) {
                    addChildToParent(children, childMenu);
                }
            }
        }
    }
}
