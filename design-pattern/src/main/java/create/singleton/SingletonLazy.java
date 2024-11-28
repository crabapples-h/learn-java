package create.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单例模式-懒汉式
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 * <p>
 * 线程安全,效率不高,可以延迟加载
 */
public class SingletonLazy {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // 类在加载时不创建对象
    private static SingletonLazy instance;

    private SingletonLazy() {
    }

    /*
     * 在调用getInstance()时检查对象是否为null
     * 如果为null就创建对象
     * 如果不为null就直接返回对象
     * 使用synchronized关键字可以避免并发情况下重复创建对象，但会造成并发情况下线程阻塞
     */
    public synchronized static SingletonLazy getInstance() {
        if (instance == null) {
            instance = new SingletonLazy();
        }
        return instance;
    }

    public void doSomething() {
        logger.info("hello,[{}]", instance);
    }
}
