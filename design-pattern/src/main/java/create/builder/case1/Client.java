package create.builder.case1;


import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;

/**
 * 建造者模式(不使用装配器)
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
        Orange orange = new Orange();
        Peach peach = new Peach();
        SweetFoodBuilder builder = new SweetFoodBuilder();
        SweetFood food = builder.builderApple(apple).builderOrange(orange).builderPeach(peach).build();
        food.eat();
    }
}
