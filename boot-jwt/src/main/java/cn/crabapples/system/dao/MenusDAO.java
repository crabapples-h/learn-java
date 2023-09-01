package cn.crabapples.system.dao;

import cn.crabapples.common.DIC;
import cn.crabapples.system.dao.mybatis.MenusMapper;
import cn.crabapples.system.dao.mybatis.RolesMapper;
import cn.crabapples.system.entity.SysMenu;
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

}
