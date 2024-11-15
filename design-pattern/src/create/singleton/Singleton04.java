package create.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 单例模式-静态内部类
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 * <p>
 * 线程安全,效率高,可以延时加载
 */
public class Singleton04 {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static class SingletonInstance {
        private static final Singleton04 instance = new Singleton04();
    }

    public static Singleton04 getInstance() {
        return SingletonInstance.instance;
    }

    private Singleton04() {
    }

    public void doSomething() {
        logger.info("hello,[{}]", SingletonInstance.instance);
    }
}
