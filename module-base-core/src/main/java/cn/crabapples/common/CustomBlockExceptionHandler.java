package cn.crabapples.common;

import cn.crabapples.common.base.ApplicationException;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义Sentinel限流异常处理器
 *
 * @author Ms.He
 * 2025-11-20 05:39
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Component
@Order(1)
public class CustomBlockExceptionHandler implements BlockExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CustomBlockExceptionHandler.class);

    /**
     * 自定义限流异常处理器
     * AuthorityException	授权规则异常
     * DegradeException	服务降级异常
     * FlowException	服务限流异常
     * ParamFlowException	热点参数限流异常
     * SystemBlockException	系统规则异常
     *
     * @param request  请求
     * @param response 响应
     * @param e        异常
     * @throws Exception 异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        logger.error("进入自定义限流异常处理器:[{}]", e.getClass().getSimpleName());
        if (e instanceof AuthorityException) {
            throw new ApplicationException("操作失败:授权规则限流");
        }
        if (e instanceof ParamFlowException) {
            throw new ApplicationException("操作失败:热点参数限流");
        }
        if (e instanceof FlowException) {
            throw new ApplicationException("操作失败:服务限流");
        }
        if (e instanceof DegradeException) {
            throw new ApplicationException("操作失败:服务降级");
        }
        if (e instanceof SystemBlockException) {
            throw new ApplicationException("操作失败:系统规则限流");
        }
        throw new ApplicationException("操作失败:Sentinel异常");
    }
}
