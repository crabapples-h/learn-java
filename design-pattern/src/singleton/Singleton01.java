package pattern23.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 单例模式-饿汉式
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 * <p>
 * 线程安全,效率高,不能延时加载
 */
public class Singleton01 {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    // 类在加载时就初始化(jvm的初始化，即给对象赋初始值)对象
    private static final Singleton01 instance = new Singleton01();

    private Singleton01() {}

    // 在调用getInstance()时真正触发初始化(即创建对象)
    public static Singleton01 getInstance() {
        return instance;
    }

    public void doSomething() {
        logger.info("hello,[{}]", instance);
    }
}
