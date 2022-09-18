package cn.crabapples.common.config.datasource.dynamicaop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * TODO 动态获取数据源的切面
 *
 * @author Mr.He
 * 9/5/20 2:30 PM
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
@Aspect
@Order(-1)//保证在@Transactional之前执行
@Component
@Slf4j
public class DynamicDataSourceAspect {
    //改变数据源
    @Before(value = "@annotation(dataSourceChange)")
    public void changeDataSource(JoinPoint joinPoint, DataSourceChange dataSourceChange) {
        log.info("数据源切换开始");
        String dataSourceId = dataSourceChange.name();
        if (!DynamicDataSourceContextHolder.containsDataSource(dataSourceId)) {
            //joinPoint.getSignature() ：获取连接点的方法签名对象
            log.info("数据源:[{}]不存在使用默认的数据源[{}]", dataSourceId, joinPoint.getSignature());
        } else {
            log.info("使用数据源：" + dataSourceId);
            DynamicDataSourceContextHolder.setDataSource(dataSourceId);
        }
        log.info("数据源切换结束");
    }

    @After("@annotation(dataSourceChange)")
    public void clearDataSource(JoinPoint joinPoint, DataSourceChange dataSourceChange) {
        log.debug("清除数据源:[{}]", dataSourceChange.name());
        DynamicDataSourceContextHolder.clearDataSource();
    }
}
