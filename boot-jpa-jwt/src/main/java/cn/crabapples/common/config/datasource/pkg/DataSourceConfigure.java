package cn.crabapples.common.config.datasource.pkg;

import cn.crabapples.common.config.datasource.aop.DynamicDataSourceContextHolder;
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
//    @Value("${spring.datasource.druid.loginUsername}")
//    String loginUsername;
//    @Value("${spring.datasource.druid.loginPassword}")
//    String loginPassword;
//    @Value("${spring.datasource.druid.deny}")
//    String deny;
//    @Value("${spring.datasource.druid.allow}")
//    String allow;
//    @Value("${spring.datasource.druid.resetEnable}")
//    String resetEnable;
//    @Value("${spring.datasource.druid.maxActive:}")
//    String maxActive;

//    @Bean
//    public ServletRegistrationBean druidServlet() { // 主要实现WEB监控的配置处理
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
//                new StatViewServlet(), "/druid/*"); // 现在要进行druid监控的配置处理操作
//        servletRegistrationBean.addInitParameter("allow", allow); // 白名单
//        servletRegistrationBean.addInitParameter("deny", deny); // 黑名单
//        servletRegistrationBean.addInitParameter("loginUsername", loginUsername); // 用户名
//        servletRegistrationBean.addInitParameter("loginPassword", loginPassword); // 密码
//        servletRegistrationBean.addInitParameter("resetEnable", resetEnable); // 是否可以重置数据源
//        servletRegistrationBean.addInitParameter("max-active", maxActive); // 最大链接数
//        return servletRegistrationBean;
//    }


    @Primary
    @Bean(value = "firstDataSource")
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource dataSourceOne() {
        log.info("Init firstDataSource");
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
