package cn.crabapples.common.config.datasource;

import cn.crabapples.common.ApplicationException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private static final String CONFIG_FILE_NAME = "datasource.properties";
    private static PropertySource<?> propertySource = null;
    // 默认数据连接池
    public static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";

    private Class<? extends DataSource> dataSourceType;

    // 默认数据源
    private DataSource defaultDataSource;

    private Map<String, DataSource> dataSourceMaps = new HashMap<String, DataSource>();

    /**
     * 加载多数据源配置
     *
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        try {
            loadConfigFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("config file [datasource.properties] load fail,please check this file");
        }
        initDefaultDataSource();
        initSlaveDataSources();
    }

    public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        try {
            Object type = dataSourceMap.get("type");
            if (type == null) {
                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
            }
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = dataSourceMap.get("driver").toString();
            String url = dataSourceMap.get("url").toString();
            String username = dataSourceMap.get("username").toString();
            String password = dataSourceMap.get("password").toString();
            // 自定义DataSource配置
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadConfigFile() throws IOException {
        PropertySourceLoader loader = new PropertiesPropertySourceLoader();
        Resource resource = new DefaultResourceLoader().getResource(CONFIG_FILE_NAME);
        List<PropertySource<?>> propertySources = loader.load(CONFIG_FILE_NAME, resource);
        for (PropertySource<?> source : propertySources) {
            if (CONFIG_FILE_NAME.equals(source.getName())) {
                propertySource = source;
            }
        }
        Assert.notNull(propertySource, "not found config file :[classpath:/datasource.properties] (找不到配置文件)");
    }

    /**
     * 初始化默认数据源
     */
    private void initDefaultDataSource() {

        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", propertySource.getProperty("datasource.primary.driverClassName"));
        dsMap.put("url", propertySource.getProperty("datasource.primary.url"));
        dsMap.put("username", propertySource.getProperty("datasource.primary.username"));
        dsMap.put("password", propertySource.getProperty("datasource.primary.password"));
        dsMap.put("type", propertySource.getProperty("datasource.primary.type"));
        defaultDataSource = buildDataSource(dsMap);
    }

    private void initSlaveDataSources() {
        // 读取配置文件获取更多数据源
        String[] names = String.valueOf(propertySource.getProperty("datasource.names")).split(",");
        for (String name : names) {
            System.err.println(name);
            // 多个数据源
            Map<String, Object> dsMap = new HashMap<>();
            dsMap.put("driver", propertySource.getProperty("datasource." + name + ".driverClassName"));
            dsMap.put("url", propertySource.getProperty("datasource." + name + ".url"));
            dsMap.put("username", propertySource.getProperty("datasource." + name + ".username"));
            dsMap.put("password", propertySource.getProperty("datasource." + name + ".password"));
            dsMap.put("type", propertySource.getProperty("datasource." + name + ".type"));
            DataSource ds = buildDataSource(dsMap);
            dataSourceMaps.put(name, ds);
        }
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<String, Object> targetDataSources = new HashMap<>();
        // 将主数据源添加到更多数据源中
        targetDataSources.put("dataSource", defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        // 添加更多数据源
        targetDataSources.putAll(dataSourceMaps);
        for (String key : dataSourceMaps.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }
        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);

        MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
        mutablePropertyValues.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mutablePropertyValues.addPropertyValue("targetDataSources", targetDataSources);
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
    }
}
