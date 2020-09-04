package cn.crabapples.common.config.datasource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)//保证在@Transactional之前执行
@Component
@Slf4j
public class DynamicDataSourceAspect {
    //改变数据源
    @Before(value = "@annotation(dataSourceChange)")
    public void changeDataSource(JoinPoint joinPoint, DataSourceChange dataSourceChange) {
        System.err.println("数据源切换开始");
        String dbid = dataSourceChange.name();
        if (!DynamicDataSourceContextHolder.containsDataSource(dbid)) {
            //joinPoint.getSignature() ：获取连接点的方法签名对象
            log.info("数据源:[{}]不存在使用默认的数据源[{}]", dbid, joinPoint.getSignature());
        } else {
            log.info("使用数据源：" + dbid);
            DynamicDataSourceContextHolder.setDataSource(dbid);
        }
        System.err.println("数据源切换结束");
    }

    @After("@annotation(dataSourceChange)")
    public void clearDataSource(JoinPoint joinPoint, DataSourceChange dataSourceChange) {
        log.debug("清除数据源:[{}]", dataSourceChange.name());
        DynamicDataSourceContextHolder.clearDataSource();
    }
}
