package demo.mybatis.anno;

import java.lang.annotation.*; /**
 * Join查询条件注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JoinCondition {
    /**
     * 关联表字段名
     */
    String field();

    /**
     * 条件操作符
     */
    String operator() default "=";

    /**
     * 值
     */
    String value();
}
