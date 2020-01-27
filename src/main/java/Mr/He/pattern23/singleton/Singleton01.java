package Mr.He.pattern23.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 单例模式-懒汉式
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 *
 * 线程安全,效率高,不能延时加载
 */
public class Singleton01{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Singleton01 instance = new Singleton01();
    private Singleton01() {}

    public static Singleton01 getInstance() {
        return instance;
    }
    public void doSomething(){
        logger.info("hello,[{}]", instance);
    }
}