package cn.crabapples.system.dao;

import cn.crabapples.common.DIC;
import cn.crabapples.system.dao.mybatis.MenusMapper;
import cn.crabapples.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public SysMenu findById(String id) {
        return getById(id);
    }

    @Transactional
    public boolean save(SysMenu entity) {
        return saveOrUpdate(entity);
    }

    public boolean remove(String id) {
        return removeById(id);
    }

    public List<SysMenu> findButtonsByIds(List<String> ids) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysMenu::getId, ids)
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
    public List<SysMenu> findMenusTree() {
        return baseMapper.findMenusTree(null);
    }

}
