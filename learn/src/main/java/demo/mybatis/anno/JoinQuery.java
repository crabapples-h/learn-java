package demo.mybatis.anno;

import java.lang.annotation.*; /**
 * Join查询配置注解（用在方法上）
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JoinQuery {
    /**
     * 需要Join的实体类字段
     */
    String[] value() default {};

    /**
     * 是否启用自动映射
     */
    boolean autoMapping() default true;
}
