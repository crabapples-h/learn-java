package thread.waitNotify;

/**
 * TODO 多线程 等待唤醒机制
 *
 * @author Ms.He
 * 2023/7/13 14:07
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) {
        new ProviderThread().start();
        new CusomerThread().start();
    }
}
