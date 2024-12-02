package create.factory.simplefactory;

/**
 * 工厂模式-简单工厂模式(静态工厂模式)
 * 简单工厂严格意义上并不是一种设计模式，只是一种思想习惯
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client {
    public static void main(String[] args) {
        // 新建工厂，使用工厂方法创建对象
        Food apple = FoodFactory.getApple();
        Food orange = FoodFactory.getOrange();
        Food peach = FoodFactory.getPeach();
        apple.eat();
        orange.eat();
        peach.eat();
    }

}
