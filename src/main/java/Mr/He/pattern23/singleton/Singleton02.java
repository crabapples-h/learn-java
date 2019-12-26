package Mr.He.pattern23.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 单例模式-饿汉式
 *
 * @author Mr.He
 * @date 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 *
 * 线程安全,效率不高,可以延迟加载
 */
public class Singleton02 {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Singleton02 instance;
    private Singleton02() {}

    public synchronized static Singleton02 getInstance() {
        if(instance == null){
            instance = new Singleton02();
        }
        return instance;
    }
    public void doSomething(){
        logger.info("hello,[{}]", instance);
    }
}
