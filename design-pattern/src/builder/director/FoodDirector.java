package pattern23.builder.director;

import pattern23.factory.simplefactory.Apple;
import pattern23.factory.simplefactory.FoodFactory;

/**
 * TODO 建造者模式-装配器
 *
 * @author Mr.He
 * 2020/7/13 13:29
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public interface FoodDirector {
    SweetFood director(Apple apple, FoodFactory.Orange orange, FoodFactory.Peach peach);
}
