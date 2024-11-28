package create.factory.simplefactory;

/**
 * TODO 工厂模式-简单工厂模式(静态工厂模式)
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class FoodFactory {
    public static Food.Apple getApple() {
        return new Food.Apple();
    }

    public static Food.Orange getOrange() {
        return new Food.Orange();
    }

    public static Food.Peach getPeach() {
        return new Food.Peach();
    }


}
