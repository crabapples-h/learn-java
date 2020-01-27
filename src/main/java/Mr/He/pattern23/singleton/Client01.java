package Mr.He.pattern23.singleton;


/**
 * TODO 单例模式测试客户端
 *
 * @author Mr.He
 * 12/19/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client01 {
    public static void main(String[] args) {
        Singleton01 singleton01 = Singleton01.getInstance();
        Singleton02 singleton02 = Singleton02.getInstance();
        Singleton03 singleton03 = Singleton03.getInstance();
        Singleton04 singleton04 = Singleton04.getInstance();
        Singleton05 singleton05 = Singleton05.INSTANCE;
        singleton01.doSomething();
        singleton02.doSomething();
        singleton03.doSomething();
        singleton04.doSomething();
        singleton05.doSomething();
    }

}
