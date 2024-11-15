package cn.crabapples.common.dic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 字典翻译注解
 *
 * @author Mr.He
 * 2023/8/30 20:30
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    /*
     * 1.当为字典表时，留空
     * 2.当为其他表时，填写表名
     */
    String dictTable() default "";

    /*
     * 1.当为字典表时，留空
     * 2.当为其他表时，填写其他表的映射的字段名
     *
     * 如
     * @Dict(dictCode = "id",dictField = "name",dictTable = "sys_gander")
     * Integer gender;
     * 表示使用sys_gander表中id=gander的数据中的name来翻译
     */
    String dictField() default "";

    /*
     * 1.当为字典表时，填写字典表code
     * 2.当为其他表时，填写其他表的关联的字段名
     */
    String dictCode();


}
