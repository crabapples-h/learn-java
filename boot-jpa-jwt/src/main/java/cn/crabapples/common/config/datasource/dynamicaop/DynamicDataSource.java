package cn.crabapples.common.config.datasource.dynamicaop;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * TODO 动态数据源(AbstractRoutingDataSource(每执行一次数据库，AOP动态获取DataSource))
 *
 * @author Mr.He
 * 9/5/20 2:30 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        /**
         * DynamicDataSourceContextHolder代码中使用setDataSource
         * 设置当前的数据源，在路由类中使用getDataSource进行获取，
         * 交给AbstractRoutingDataSource进行注入使用。
         */
        return DynamicDataSourceContextHolder.getDataSource();
    }
}
