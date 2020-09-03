package cn.crabapples.common.config.datasource;

import java.lang.annotation.*;

/**
 * @Description 作用于类、接口或者方法上
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceChange {
    String name();
}
