package cn.crabapples.spring.service;

import cn.crabapples.spring.dto.ResponseDTO;
import cn.crabapples.spring.form.UserForm;

import java.util.Set;

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
     * @return 登录成功后返回的登录信息
     */
    ResponseDTO login(UserForm form);

    /**
     * 获取系统菜单
     * @return
     */
    Set<? extends Object> getSysMenus();
}
