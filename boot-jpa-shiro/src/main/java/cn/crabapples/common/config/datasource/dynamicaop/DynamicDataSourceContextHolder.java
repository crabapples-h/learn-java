package cn.crabapples.common.config.datasource.dynamicaop;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 数据源信息临时存储类
 *
 * @author Mr.He
 * 9/5/20 2:31 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class DynamicDataSourceContextHolder {
    // 线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<>();
    // 管理所有的数据源Id
    public static List<String> dataSourceIds = new ArrayList<>();

    public static void setDataSource(String dataSource) {
        dataSources.set(dataSource);
    }

    public static String getDataSource() {
        return dataSources.get();
    }

    public static void clearDataSource() {
        dataSources.remove();
    }

    // 判断指定的DataSource当前是否存在
    public static boolean containsDataSource(String dataSourceId) {
        return dataSourceIds.contains(dataSourceId);
    }
}
