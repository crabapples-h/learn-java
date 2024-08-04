package builder.build;

import pattern23.factory.simplefactory.Apple;
import pattern23.factory.simplefactory.FoodFactory;

/**
 * TODO 建造者模式
 *
 * @author Mr.He
 * 2020/7/13 13:27
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class Client {
    public static void main(String[] args) {
        Apple apple = new Apple();
        FoodFactory.Orange orange = new FoodFactory.Orange();
        FoodFactory.Peach peach = new FoodFactory.Peach();
        SweetFoodBuilder builder = new SweetFoodBuilder();
        SweetFood food = builder.builderApple(apple).builderOrange(orange).builderPeach(peach).build();
        food.eat();
    }
}
