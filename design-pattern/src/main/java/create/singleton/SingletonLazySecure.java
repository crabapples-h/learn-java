package create.singleton;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 单例模式-懒汉式(防止破解单例模式)
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 * <p>
 * 线程安全,效率高,不能延时加载
 */
public class SingletonLazySecure implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Getter
    private static final SingletonLazySecure instance = new SingletonLazySecure();

    private SingletonLazySecure() {
        if (instance != null) {
            throw new RuntimeException("禁止通过反射创建多个实例");
        }
    }

    public void doSomething() {
        logger.info("hello,[{}]", instance);
    }

    /**
     * 反序列化时如果对象存在则直接返回该对象
     *
     * @return 单例对象
     * @throws ObjectStreamException 反序列化异常
     */
    private Object readResolve() throws ObjectStreamException {
        return instance;
//        throw new RuntimeException("禁止通过反序列化创建多个实例");
    }
}
