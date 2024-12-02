package create.builder.director.case1;


import create.builder.director.case1.builder.SweetFoodBuilder;
import create.builder.director.case1.director.SweetFoodDirector;
import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;

/**
 * 建造者模式-标准式
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
        Orange orange = new Orange();
        Peach peach = new Peach();
        Food sweetFood = new SweetFoodDirector(builder).director(apple, orange, peach);
        sweetFood.eat();
    }
}
