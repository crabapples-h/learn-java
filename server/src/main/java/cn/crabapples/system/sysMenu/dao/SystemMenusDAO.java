package cn.crabapples.system.sysMenu.dao;

import cn.crabapples.common.dic.DIC;
import cn.crabapples.system.sysMenu.dao.mybatis.mapper.MenusMapper;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysMenu.form.MenusForm;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    public List<SysMenu> getChildList(String pid) {
        return baseMapper.findMenusList(pid);
//        return mapper.selectListByQuery(new QueryWrapper().eq(SysMenu::getPid, pid));
    }

    public boolean remove(String id) {
        return baseMapper.deleteById(id) > 0;
    }

    public List<SysMenu> findButtonsByIds(List<String> ids) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>(SysMenu.class)
                .in(SysMenu::getId, ids)
                .eq(SysMenu::getMenusType, DIC.MENUS_TYPE_BUTTON);
        return list(wrapper);
    }

    // 查询用户的菜单列表
    public List<SysMenu> getUserMenus(String id) {
        return baseMapper.getUserMenus(id);
    }

    /**
     * 查询所有菜单树 一对多递归
     *
     * @return 菜单树
     */
    public List<SysMenu> findMenusTreeList() {
        return baseMapper.findMenusTree(null);
    }

    public Page<SysMenu> getMenuTreePage(Page<SysMenu> page) {
        return baseMapper.findMenusTreePage(page, null);
    }


    public Page<SysMenu> getMenuListPage(Page<SysMenu> page, MenusForm form) {
//        return mapper.paginate(page, QueryWrapper.create()
//                .isNull(SysMenu::getPid)
//                .leftJoin(SysMenu.class).as("child")
//                .on(SysMenu::getId, SysMenu::getPid)
//                .orderBy(SysMenu::getSort, true));
        return baseMapper.findMenusListPage(page);
    }
}
