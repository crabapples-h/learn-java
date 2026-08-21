package cn.crabapples.common.jwt;

/**
 * TODO 用户态校验器（接口反转，避免 module-base-core 反向依赖 module-system）
 *
 * 由具体的用户管理模块（如 module-system）提供实现，
 * JwtInterceptor 通过此接口在每次请求时校验用户状态：
 *   - 用户是否存在
 *   - 是否被锁定（status == 0）
 *   - 是否被删除（delFlag == 0）
 * 失败抛 401。
 *
 * 如果 classpath 中没有实现（如 socket-app、gateway-app 等纯网关/服务），
 * JwtInterceptor 会通过 @Autowired(required=false) 跳过校验，保持向后兼容。
 *
 * @author Mr.He
 */
public interface UserAuthChecker {
    /**
     * 校验 token 对应的用户是否仍处于有效状态
     *
     * @param userId 从 token 中解析出的用户 ID
     * @return true=有效（status=0 && delFlag=0），false=失效
     */
    boolean isUserActive(String userId);
}
