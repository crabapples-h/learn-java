package create.factory.simplefactory;

import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;

/**
 * 工厂模式-简单工厂模式(静态工厂模式)
 * 工厂方法
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class FoodFactory {
    public static Apple getApple() {
        return new Apple();
    }

    public static Orange getOrange() {
        return new Orange();
    }

    public static Peach getPeach() {
        return new Peach();
    }
}