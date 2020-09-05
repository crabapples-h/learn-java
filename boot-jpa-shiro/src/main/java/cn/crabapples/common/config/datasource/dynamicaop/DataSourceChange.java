package cn.crabapples.common.config.datasource.dynamicaop;

import java.lang.annotation.*;

/**
 * TODO 动态切换数据图的注解
 *
 * @author Mr.He
 * 9/5/20 2:29 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceChange {
    String name();
}
