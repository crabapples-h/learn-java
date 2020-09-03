package cn.crabapples.system.service;

import cn.crabapples.system.entity.SysMenu;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;

import java.util.List;

/**
 * TODO 系统相关服务
 *
 * @author Mr.He
 * 2020/1/28 23:22
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name 29404
 */
public interface SysService {
    /**
     * 用户登录
     * @return 登录成功后返回token
     */
    String loginCheck(UserForm form);

    /**
     * 获取当前用户系统菜单
     * @return 当前用户拥有的菜单
     */
    List<SysMenu> getSysMenus(SysUser user);
}
