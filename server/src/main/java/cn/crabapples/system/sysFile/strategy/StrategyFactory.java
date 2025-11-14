package cn.crabapples.system.sysFile.strategy;

import cn.crabapples.system.sysFile.UPLOAD_TYPE;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
public class StrategyFactory implements ApplicationContextAware {
    private ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public UploadFileStrategy getBean(UPLOAD_TYPE upload) {
        String type = upload.type;
        return context.getBean(type, UploadFileStrategy.class);
    }
}
