package create.builder.case1;


import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;

/**
 * TODO 建造者模式-建造器
 *
 * @author Mr.He
 * 2020/7/13 13:28
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public interface FoodBuilder {

    FoodBuilder builderApple(Apple apple);

    FoodBuilder builderOrange(Orange orange);

    FoodBuilder builderPeach(Peach peach);

    Food build();
}
