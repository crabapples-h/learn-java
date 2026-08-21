package cn.crabapples.system.sysUser.auth;

import cn.crabapples.common.dic.DIC;
import cn.crabapples.common.jwt.UserAuthChecker;
import cn.crabapples.system.sysUser.dao.UserDAO;
import cn.crabapples.system.sysUser.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * TODO 用户态校验器实现（修复 P0-1）
 *
 * 由 module-system 提供，被 module-base-core 的 JwtInterceptor 自动注入。
 * 校验逻辑：
 *   - 用户不存在 → false（失效）
 *   - status == DIC.USER_LOCK（锁定）→ false
 *   - delFlag != DIC.NOT_DEL（已删除）→ false
 * 其余情况 → true
 *
 * @author Mr.He
 */
@Slf4j
@Component
public class SysUserAuthCheckerImpl implements UserAuthChecker {

    private final UserDAO userDAO;

    public SysUserAuthCheckerImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean isUserActive(String userId) {
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        try {
            SysUser user = userDAO.findById(userId);
            if (user == null) {
                log.warn("用户不存在 userId=[{}]", userId);
                return false;
            }
            if (user.getStatus() != null && user.getStatus() == DIC.USER_LOCK) {
                log.warn("用户已被锁定 userId=[{}]", userId);
                return false;
            }
            if (user.getDelFlag() != null && user.getDelFlag() != DIC.NOT_DEL) {
                log.warn("用户已被删除 userId=[{}]", userId);
                return false;
            }
            return true;
        } catch (Exception e) {
            // 数据库异常时保守放行（避免一刀切拒绝所有请求），但记录日志便于排查
            log.error("用户态校验异常 userId=[{}]，放行", userId, e);
            return true;
        }
    }
}
