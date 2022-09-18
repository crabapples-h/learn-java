package cn.crabapples.common.config.datasource.packagename;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

/**
 * TODO 辅数据源配置(根据包路径切换数据源)
 *
 * @author Mr.He
 * 9/5/20 2:22 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */

@Configuration
@EnableJpaRepositories(basePackages = "cn.crabapples.test.dao",
        entityManagerFactoryRef = "localContainerEntityManagerFactoryBean",
        transactionManagerRef = "platformTransactionManager")
//@EntityScan("cn.crabapples.system.entity")
public class DataSourceSecondConfigure {

    private final DataSource dataSource;

    private final JpaProperties jpaProperties;

    private final HibernateProperties hibernateProperties;

    public DataSourceSecondConfigure(@Qualifier(value = "secondDataSource") DataSource dataSource,
                                     JpaProperties jpaProperties,
                                     HibernateProperties hibernateProperties) {
        this.dataSource = dataSource;
        this.jpaProperties = jpaProperties;
        this.hibernateProperties = hibernateProperties;
    }


    @Bean
    LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("cn.crabapples.system.entity")
                .persistenceUnit("mainDatabase").build();
    }

    @Bean
    PlatformTransactionManager platformTransactionManager(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = localContainerEntityManagerFactoryBean(builder);
        return new JpaTransactionManager(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getObject()));
    }
}
