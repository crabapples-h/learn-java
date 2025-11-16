package demo.mybatis.anno;

import java.lang.annotation.*;

/**
 * Join查询注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JoinField {
    /**
     * 关联的表名
     */
    String table();

    /**
     * 关联表的主键字段
     */
    String primaryKey();

    /**
     * 当前表的关联字段
     */
    String foreignKey();

    /**
     * 关联类型
     */
    JoinType type() default JoinType.LEFT_JOIN;

    /**
     * 需要查询的关联表字段，默认查询所有
     */
    String[] selectFields() default {"*"};

    /**
     * 关联表别名
     */
    String alias() default "";

}
