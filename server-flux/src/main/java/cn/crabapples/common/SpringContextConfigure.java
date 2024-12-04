package cn.crabapples.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringContextConfigure implements ApplicationContextAware {

    private static volatile BeanFactory context;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }
    public static Object getBean(String name) {
        return context.getBean(name);
    }

}
