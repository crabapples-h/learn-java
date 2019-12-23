package Mr.He.pattern23.factory.simplefactory;

/**
 * TODO 工厂模式-简单工厂模式(静态工厂模式)
 *
 * @author Mr.He
 * @date 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client {
    public static void main(String[] args) {
        Food apple = FoodFactory.getApple();
        Food orange = FoodFactory.getOrange();
        Food peach = FoodFactory.getPeach();
        apple.eat();
        orange.eat();
        peach.eat();
    }
}
