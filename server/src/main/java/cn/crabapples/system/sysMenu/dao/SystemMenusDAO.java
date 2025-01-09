package cn.crabapples.system.sysMenu.dao;

import cn.crabapples.common.dic.DIC;
import cn.crabapples.system.sysMenu.dao.mybatis.mapper.MenusMapper;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import com.mybatisflex.core.paginate.Page;
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
public class SystemMenusDAO extends ServiceImpl<MenusMapper, SysMenu> {

    public boolean remove(String id) {
//        return baseMapper.removeById(id);
        return mapper.deleteById(id) > 0;
    }

    public List<SysMenu> findButtonsByIds(List<String> ids) {
        QueryWrapper wrapper = QueryWrapper.create(SysMenu.class);
        wrapper.in(SysMenu::getId, ids)
                .eq(SysMenu::getMenusType, DIC.MENUS_TYPE_BUTTON);
        return list(wrapper);
    }

    // 查询用户的菜单列表
    public List<SysMenu> getUserMenus(String id) {
        return mapper.getUserMenus(id);
    }

    /**
     * 查询所有菜单树 一对多递归
     *
     * @return 菜单树
     */
    public List<SysMenu> findMenusTree() {
        return mapper.findMenusTree(null);
    }

    public Page<SysMenu> getMenuPage(Page<SysMenu> page) {
        return mapper.xmlPaginate("findMenusTreePage",page, QueryWrapper.create());
    }
}
