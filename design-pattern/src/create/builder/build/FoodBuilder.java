package create.builder.build;


import create.factory.simplefactory.Apple;
import create.factory.simplefactory.FoodFactory;

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

    FoodBuilder builderOrange(FoodFactory.Orange orange);

    FoodBuilder builderPeach(FoodFactory.Peach peach);

    Food build();
}
