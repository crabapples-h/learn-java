package pattern23.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 单例模式-双重检测锁(由于jvm底层模型原因,可能会出问题,不建议使用)
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Singleton03 {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Singleton03 instance;

    private Singleton03() {}

    public static Singleton03 getInstance() {
        if(instance == null){
            Singleton03 singleton03;
            synchronized (Singleton03.class){
                singleton03 = instance;
                if(singleton03 == null){
                    synchronized (Singleton03.class){
                        if(singleton03 == null){
                            singleton03 = new Singleton03();
                        }
                    }
                    instance = singleton03;
                }
            }
        }
        return instance;
    }
    public void doSomething(){
        logger.info("hello,[{}]", instance);
    }
}
