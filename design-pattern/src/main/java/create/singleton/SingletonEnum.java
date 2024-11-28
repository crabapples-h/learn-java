package create.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单例模式-枚举模式 (可以避免反射和反序列化的漏洞！)
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 * <p>
 * 线程安全,效率高,不能延时加载
 */
public enum SingletonEnum {
    INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static SingletonEnum getInstance() {
        return INSTANCE;
    }

    public void doSomething() {
        logger.info("hello,[{}]", INSTANCE);
    }

}
