package cn.crabapples.common.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InterceptorRegister {

//    @Autowired
//    private TenantHandler tenantHandler;
//
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

//         多租户插件
//        TenantLineInnerInterceptor tenantInterceptor = new TenantLineInnerInterceptor();
//        tenantInterceptor.setTenantLineHandler(tenantHandler);
//        interceptor.addInnerInterceptor(tenantInterceptor);

        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

/// /    @Bean
/// /    public MybatisConfiguration configuration(MybatisConfiguration configuration) {
/// /        // 数据权限插件
/// /        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor(new PermissionHandler());
/// ///        configuration.addInterceptor(dataPermissionInterceptor);
/// /        return configuration;
/// /    }


}
