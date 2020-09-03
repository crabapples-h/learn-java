//package cn.crabapples.common.config.datasource;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//
//import javax.sql.DataSource;
//
//@PropertySource("classpath:/datasource-second.properties")
//public class DataSourceSecondConfigure {
////    @Primary
//    @Bean(name = "secondDataSource")
////    @ConfigurationProperties(prefix = "spring.datasource.druid.second")
//    public DataSource getDataSource() {
//        return new DruidDataSource();
//    }
////
////    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
////    @Bean(name = "slaveDruidDataSource")
////    public DataSource slaveDruidDataSource(Environment environment) {
////        return DruidDataSourceBuilder.create().build(environment, "spring.datasource.druid.slave.");
////    }
//
//
//}
