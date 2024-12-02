package create.builder.case2;


import create.builder.case2.builder.SweetFoodBuilder;
import create.builder.case2.director.SweetFoodDirector;
import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;

/**
 * 建造者模式(使用装配器)-标准式
 *
 * @author Mr.He
 * 2020/7/13 13:27
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class Client {
    public static void main(String[] args) {
        // 使用建造器创建三个对象
        SweetFoodBuilder builder = new SweetFoodBuilder();
        Apple apple = new Apple();
        Orange orange = new Orange();
        Peach peach = new Peach();
        // 使用装配器将三个对象组装成为新的对象
        Food sweetFood = new SweetFoodDirector(builder).director(apple, orange, peach);
        sweetFood.eat();
    }
}
