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
public class FoodFactory {
    public static Food getApple(){
        return new Apple();
    }
    public static Food getOrange(){
        return new Orange();
    }
    public static Food getPeach(){
        return new Peach();
    }
}
