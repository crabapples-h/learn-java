package create.builder.case3.builder;

import create.factory.simplefactory.FoodFactory;
import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;

/**
 * TODO 建造者模式-建造器(实现类)
 *
 * @author Mr.He
 * 2020/7/13 13:29
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class SweetFoodBuilder implements FoodBuilder {
    @Override
    public Apple builderApple() {
        return FoodFactory.getApple();
    }

    @Override
    public Orange builderOrange() {
        return FoodFactory.getOrange();
    }

    @Override
    public Peach builderPeach() {
        return FoodFactory.getPeach();
    }
}
