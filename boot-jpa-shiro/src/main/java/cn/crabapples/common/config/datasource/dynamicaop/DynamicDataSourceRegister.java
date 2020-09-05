package cn.crabapples.common.config.datasource.dynamicaop;

import cn.crabapples.common.ApplicationException;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
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

/**
 * TODO 动态数据源注册类(需要在主启动类上使用@Import注入)
 *
 * @author Mr.He
 * 9/5/20 2:32 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
    private static final String CONFIG_FILE_NAME = "datasource.properties";
    private static PropertySource<?> propertySource = null;
    // 默认数据连接池
    public static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";

    // 默认数据源
    private DataSource defaultDataSource;

    private Map<String, DataSource> dataSourceMaps = new HashMap<String, DataSource>();

    /**
     * 加载多数据源配置
     */
    @Override
    public void setEnvironment(Environment environment) {
        log.info("开始注册多数据源配置");
        try {
            log.debug("开始读取多数据源配置文件:[{}]", CONFIG_FILE_NAME);
            loadConfigFile();
            log.info("多数据源配置文件读取完成:[{}]", propertySource);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("config file [datasource.properties] load fail,please check this file");
        }
        log.debug("开始初始化默认数据源");
        initDefaultDataSource();
        log.debug("开始初始化其他数据源");
        initSlaveDataSources();
        log.info("多数据源配置注册完成");

    }

    private DataSource buildDataSource(Map<String, Object> dataSourceMap) {
        log.debug("开始构建数据源:[{}]", dataSourceMap);
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
            DataSource dataSource = factory.build();
            log.debug("数据源构建完成:[{}]", dataSource);
            return dataSource;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadConfigFile() throws IOException {
        log.debug("开始读取配置文件");
        PropertySourceLoader loader = new PropertiesPropertySourceLoader();
        Resource resource = new DefaultResourceLoader().getResource(CONFIG_FILE_NAME);
        List<PropertySource<?>> propertySources = loader.load(CONFIG_FILE_NAME, resource);
        for (PropertySource<?> source : propertySources) {
            String fileName = source.getName();
            log.debug("当前配置文件名称:[{}]", fileName);
            if (CONFIG_FILE_NAME.equals(fileName)) {
                log.debug("配置文件名称匹配成功:[{}]", fileName);
                propertySource = source;
            }
        }
        Assert.notNull(propertySource, "not found config file :[classpath:/datasource.properties] (找不到配置文件)");
    }

    /**
     * 初始化默认数据源
     */
    private void initDefaultDataSource() {
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", propertySource.getProperty("datasource.primary.driverClassName"));
        dsMap.put("url", propertySource.getProperty("datasource.primary.url"));
        dsMap.put("username", propertySource.getProperty("datasource.primary.username"));
        dsMap.put("password", propertySource.getProperty("datasource.primary.password"));
        dsMap.put("type", propertySource.getProperty("datasource.primary.type"));
        defaultDataSource = buildDataSource(dsMap);
    }

    private void initSlaveDataSources() {
        log.info("开始构建多数据源");
        // 读取配置文件获取更多数据源
        String[] names = String.valueOf(propertySource.getProperty("datasource.names")).split(",");
        for (String name : names) {
            log.debug("当前构建的数据源名称为:[{}]", name);
            // 多个数据源
            Map<String, Object> dsMap = new HashMap<>();
            dsMap.put("driver", propertySource.getProperty("datasource." + name + ".driverClassName"));
            dsMap.put("url", propertySource.getProperty("datasource." + name + ".url"));
            dsMap.put("username", propertySource.getProperty("datasource." + name + ".username"));
            dsMap.put("password", propertySource.getProperty("datasource." + name + ".password"));
            dsMap.put("type", propertySource.getProperty("datasource." + name + ".type"));
            DataSource dataSource = buildDataSource(dsMap);
            log.debug("数据源:[{}]构建完成:[{}]", name, dataSource);
            dataSourceMaps.put(name, dataSource);
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
        DynamicDataSourceContextHolder.dataSourceIds.addAll(dataSourceMaps.keySet());
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
