package pattern23.strategy;

/**
 * TODO 策略模式
 * 定义了算法族，分别封装起来，让他们之间可以相互替换
 *
 * @author Mr.He
 * 2023/2/27 18:03
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    public static void main(String[] args) {
        Person person1 = new Woman(new Eat());
        Person person2 = new Man(new Sing());
        person1.showInterest();
        person2.showInterest();
    }
}
