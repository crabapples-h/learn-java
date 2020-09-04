package cn.crabapples.common.config.datasource;

import java.util.ArrayList;
import java.util.List;

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
