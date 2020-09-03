//package cn.crabapples.common.config.datasource;
//
//import com.alibaba.fastjson.JSON;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
//import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
//import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * 主Mysql数据库连接
// *
// * @author liyuchen@gogpay.cn
// * @date 2019-10-23 11:21
// */
//
//@Configuration
//@EnableJpaRepositories(basePackages = "org.example.dao",
//        entityManagerFactoryRef = "localContainerEntityManagerFactoryBeanPrimary",
//        transactionManagerRef = "platformTransactionManagerPrimary")
//@EntityScan("org.example.entity")
//public class DataSourcePrimaryConfigure {
//    private final DataSource primaryDataSource;
//
//    private final JpaProperties jpaProperties;
//
//    private final HibernateProperties hibernateProperties;
//
//    public DataSourcePrimaryConfigure(DataSource primaryDataSource, JpaProperties jpaProperties, HibernateProperties hibernateProperties) {
//        this.primaryDataSource = primaryDataSource;
//        this.jpaProperties = jpaProperties;
//        this.hibernateProperties = hibernateProperties;
//    }
//
//
//    @Bean
//    @Primary
//    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBeanPrimary(EntityManagerFactoryBuilder builder) {
//        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
//                jpaProperties.getProperties(),
//                new HibernateSettings());
//       Map<String, String> properties1 =  jpaProperties.getProperties();
//        System.out.println("配置文件---->" + JSON.toJSON(properties));
//        System.out.println("配置文件---->" + JSON.toJSON(properties1));
//        return builder
//                .dataSource(primaryDataSource)
//                .properties(properties1)
//                .packages("org.example.entity")
//                .persistenceUnit("mainDatabase").build();
//
//    }
//
//    @Bean
//    @Primary
//    PlatformTransactionManager platformTransactionManagerPrimary(EntityManagerFactoryBuilder builder) {
//        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = localContainerEntityManagerFactoryBeanPrimary(builder);
//        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
//    }
//}
