package cn.crabapples.common.mybatis.plugins.join;


import java.lang.annotation.*;

/**
 * Join查询注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JoinField {
    String table(); // 关联的表名

    Class<?> tableClass() default void.class; // 关联的类(和表名二选一即可,优先使用表名)

    String selfKey(); // 当前表的关联字段

    String targetKey(); // 被关联表的字段

    JoinType type() default JoinType.LEFT_JOIN; //关联类型

    String alias() default ""; //关联表别名

    String selectFields(); //需要查询的关联表字段，默认查询所有

}
