package doing.strategy;

import doing.strategy.interest.Eat;
import doing.strategy.interest.Sing;
import doing.strategy.persons.Man;
import doing.strategy.persons.Woman;

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
        // 根据不同的策略创建对象，调用相同的方法可以获取不同的结果
        Person person1 = new Woman(new Eat());
        Person person2 = new Man(new Sing());
        person1.showInterest();
        person2.showInterest();
    }
}
