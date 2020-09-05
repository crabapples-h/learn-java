package cn.crabapples.common.utils.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 标记不需要jwt拦截的url
 *
 * @author Mr.He
 * 9/5/20 2:54 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtIgnore {
}
