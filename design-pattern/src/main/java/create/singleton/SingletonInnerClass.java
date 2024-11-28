package create.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单例模式-静态内部类
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 * <p>
 * 线程安全,效率高,可以延时加载
 */
public class SingletonInnerClass {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static class SingletonInstance {
        private static final SingletonInnerClass instance = new SingletonInnerClass();
    }

    public static SingletonInnerClass getInstance() {
        return SingletonInstance.instance;
    }

    private SingletonInnerClass() {
    }

    public void doSomething() {
        logger.info("hello,[{}]", SingletonInstance.instance);
    }
}
