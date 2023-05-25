package cn.crabapples.common.config;

import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.service.SystemService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * TODO 实现jpa审计功能
 *
 * @author Mr.He
 * 2022/6/9 20:21
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
@Configuration
public class JpaAuditorAware implements AuditorAware<String> {
    private final SystemService systemService;

    public JpaAuditorAware(SystemService systemService) {
        this.systemService = systemService;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        SysUser userInfo = systemService.getUserInfo();
        return Optional.ofNullable(userInfo.getUsername());
    }
}
