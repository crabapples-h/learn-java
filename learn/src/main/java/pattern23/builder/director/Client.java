package pattern23.builder.director;

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
        SweetFoodBuilder builder = new SweetFoodBuilder();
        Apple apple = new Apple();
        FoodFactory.Orange orange = new FoodFactory.Orange();
        FoodFactory.Peach peach = new FoodFactory.Peach();
        Food sweetFood = new SweetFoodDirector(builder).director(apple, orange, peach);
        sweetFood.eat();
    }
}
