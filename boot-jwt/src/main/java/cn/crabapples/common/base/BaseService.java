package cn.crabapples.common.base;

import cn.crabapples.common.jwt.JwtConfigure;
import cn.crabapples.common.jwt.JwtTokenUtils;
import cn.crabapples.system.entity.SysUser;
import cn.crabapples.system.service.SystemUserService;
import io.jsonwebtoken.Claims;
import org.springframework.data.domain.PageRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * TODO 系统服务基础方法
 *
 * @author Mr.He
 * 2021/4/9 1:51
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public interface BaseService<T extends BaseEntity> {
    default PageRequest getJpaPage(BaseForm<T> form) {
        if (Objects.nonNull(form)) {
            Integer pageIndex = form.getPageIndex();
            Integer pageSize = form.getPageSize();
            if (Objects.nonNull(pageIndex) && Objects.nonNull(pageSize)) {
                return PageRequest.of(pageIndex, pageSize);
            }
        }
        return PageRequest.of(0, 999999999);
    }
    default SysUser getSysUserInfo(HttpServletRequest request, JwtConfigure configure, SystemUserService service) {
        final String authHeader = request.getHeader(configure.getAuthKey());
        Claims claims = JwtTokenUtils.parseJWT(authHeader, configure.getBase64Secret());
        String userId = String.valueOf(claims.get("userId"));
        return service.findById(userId);
    }
}
