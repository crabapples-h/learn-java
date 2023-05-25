package thread.callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * TODO 多线程-实现Callable接口(可获取返回值)
 *
 * @author Mr.He
 * 2023/5/26 0:29
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(myCallable);
        Thread thread1 = new Thread(futureTask);
        Thread thread2 = new Thread(futureTask);
        thread1.start();
        thread2.start();
        System.out.println(futureTask.get());
        System.out.println(futureTask.get());
    }
}
