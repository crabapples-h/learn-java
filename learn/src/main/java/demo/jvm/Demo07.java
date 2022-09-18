package demo.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 堆内存溢出
 *
 * @author Mr.He
 * 2022/5/29 12:10
 * pc-name mrhe
 */
public class Demo07 {

    public static void main(String[] args) throws InterruptedException {
        String str = "hello";
        int i =0;
        List<String> list = new ArrayList<>();
        while (true){
            i++;
            TimeUnit.SECONDS.sleep(3);
            list.add(str);
            str +=str;
            System.err.println(i);
        }
    }

}
