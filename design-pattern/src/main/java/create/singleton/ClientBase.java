package create.singleton;


/**
 * 单例模式测试客户端
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class ClientBase {
    public static void main(String[] args) {
        SingletonHunger singleton01 = SingletonHunger.getInstance();
        SingletonLazy singleton02 = SingletonLazy.getInstance();
        SingletonDoubleLock singleton03 = SingletonDoubleLock.getInstance();
        SingletonInnerClass singleton04 = SingletonInnerClass.getInstance();
        SingletonEnum singleton05 = SingletonEnum.getInstance();
        singleton01.doSomething();
        singleton02.doSomething();
        singleton03.doSomething();
        singleton04.doSomething();
        singleton05.doSomething();
    }

}
