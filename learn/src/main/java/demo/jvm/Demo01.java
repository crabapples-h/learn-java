package demo.jvm;

/**
 * 内存安全演示
 *
 * @author Mr.He
 * 2022/5/28 19:16
 * pc-name mrhe
 */
public class Demo01 {
    public static void main(String[] args) {
        methode_01();
    }

    static void methode_01() {
        System.out.println("methode_01");
        int a, b, c = 1;
        methode_02();
    }

    static void methode_02() {
        int d, e, f = 1;
        System.out.println("methode_02");
    }
}
