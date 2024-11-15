package cn.crabapples.system.sysMenu.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.sysMenu.entity.SysMenu;
import cn.crabapples.system.sysMenu.form.MenusForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * TODO 系统相关服务[菜单]
 *
 * @author Mr.He
 * 2021/4/25 0:34
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public interface SystemMenusService extends BaseService {

    List<SysMenu> getUserMenusTree();

    List<SysMenu> getMenusList();

    IPage<SysMenu> getMenuPage(Integer pageIndex, Integer pageSize, MenusForm form);

    boolean saveMenus(MenusForm form);

    boolean removeMenus(String id);

}
