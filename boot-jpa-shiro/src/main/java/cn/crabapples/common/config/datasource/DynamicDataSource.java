package cn.crabapples.common.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @Description 动态数据源
 * AbstractRoutingDataSource(每执行一次数据库，动态获取DataSource)
 */
//@Component
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * ThreadLocal 用于提供线程局部变量，在多线程环境可以保证各个线程里的变量独立于其它线程里的变量。
     * 也就是说 ThreadLocal 可以为每个线程创建一个【单独的变量副本】，相当于线程的 private static 类型变量。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

//    /**
//     * 决定使用哪个数据源之前需要把多个数据源的信息以及默认数据源信息配置好
//     *
//     * @param defaultTargetDataSource 默认数据源
//     * @param targetDataSources       目标数据源
//     */
//    public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
//        super.setDefaultTargetDataSource(defaultTargetDataSource);
//        super.setTargetDataSources(targetDataSources);
//        super.afterPropertiesSet();
//    }

    @Override
    protected Object determineCurrentLookupKey() {
        /**
         * DynamicDataSourceContextHolder代码中使用setDataSource
         * 设置当前的数据源，在路由类中使用getDataSource进行获取，
         * 交给AbstractRoutingDataSource进行注入使用。
         */
        return DynamicDataSourceContextHolder.getDataSource();
    }

    public static void setDataSource(String dataSource) {
        CONTEXT_HOLDER.set(dataSource);
    }

    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
