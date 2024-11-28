package create.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单例模式-双重检测锁(由于jvm底层模型原因,可能会出问题,不建议使用)
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class SingletonDoubleLock {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // 使用volatile关键字可以防止指令重排
    private volatile static SingletonDoubleLock instance;

    private SingletonDoubleLock() {
    }

    public static SingletonDoubleLock getInstance() {
        if (instance == null) {
            synchronized (SingletonDoubleLock.class) {
                if (instance == null) {
                    synchronized (SingletonDoubleLock.class) {
                        if (instance == null) {
                            instance = new SingletonDoubleLock();
                            /*
                             * 字节码层：
                             * JIT,CPU等会对字节码进行优化(如指令重排等)
                             * ----优化前----
                             * 1.分配空间(开辟内存空间，开辟内存空间后即已产生内存地址)
                             * 2.初始化(创建单例对象)
                             * 3.引用赋值(将内存地址指向变量)
                             * ----优化前----
                             * 1.分配空间(开辟内存空间，开辟内存空间后即已产生内存地址)
                             * 2.引用赋值(将内存地址指向变量)
                             * 3.初始化(创建单例对象)
                             * ----
                             * 如果有两个线程同时获取单例对象
                             * 当线程T1已经完成 [赋值] 但尚未 [初始化]
                             * 线程T2获取单例对象时，判断 instance == null 结果为 false
                             * 此时单例对象仅仅只完成 [赋值] 并未 [初始化]，此时就会导致空指针异常
                             * 使用 volatile 关键字，可以保证该关键字修饰的对象相关的代码不会被指令重排
                             */
                        }
                    }
                }
            }
        }
        return instance;
    }

    public void doSomething() {
        logger.info("hello,[{}]", instance);
    }
}
