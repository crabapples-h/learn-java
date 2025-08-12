package cn.crabapples.system.system.service;

import cn.crabapples.common.base.BaseService;
import cn.crabapples.system.dto.SysOptionDTO;
import cn.crabapples.system.sysUser.form.UserForm;

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

    List<String> getUserPermissions();

    boolean checkUsername(String username);

    List<SysOptionDTO> dictSelectOptions(String code);
}
