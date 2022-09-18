package cn.crabapples.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.form.UserForm;

import javax.servlet.http.HttpServletRequest;
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
public interface SystemService extends BaseService {

    String login(UserForm form);

    List<String> getUserPermissions(HttpServletRequest request);

    SysUser getUserInfo(HttpServletRequest request);

    boolean checkUsername(String username);

}
