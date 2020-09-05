package cn.crabapples.common.config.datasource.packagename;

import cn.crabapples.common.config.datasource.dynamicaop.DynamicDataSourceContextHolder;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * TODO 数据源配置
 *
 * @author Mr.He
 * 9/5/20 2:34 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Slf4j
@Configuration
public class DataSourceConfigure {

//    @Bean
//    public ServletRegistrationBean druidServlet() { // 主要实现WEB监控的配置处理
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
//                new StatViewServlet(), "/druid/*"); // 现在要进行druid监控的配置处理操作
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1,10.1.1.1"); // 白名单
//        // servletRegistrationBean.addInitParameter("deny", "192.168.1.200"); // 黑名单
//        servletRegistrationBean.addInitParameter("loginUsername", "admin"); // 用户名
//        servletRegistrationBean.addInitParameter("loginPassword", "admin"); // 密码
//        servletRegistrationBean.addInitParameter("resetEnable", "false"); // 是否可以重置数据源
//        servletRegistrationBean.addInitParameter("max-active", "20"); // 是否可以重置数据源
//        return servletRegistrationBean ;
//    }
//
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean() ;
//        filterRegistrationBean.setFilter(new WebStatFilter());
//        filterRegistrationBean.addUrlPatterns("/*"); // 所有请求进行监控处理
//        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");
//        return filterRegistrationBean ;
//    }

    @Primary
    @Bean(value = "firstDataSource")
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource dataSourceOne() {
        log.info("Init DataSourceOne");
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        DynamicDataSourceContextHolder.dataSourceIds.add("firstDataSource");
        return dataSource;
    }

    @Bean(value = "secondDataSource")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource dataSourceTwo() {
        log.info("Init DataSourceTwo");
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        DynamicDataSourceContextHolder.dataSourceIds.add("secondDataSource");
        return dataSource;
    }
}
