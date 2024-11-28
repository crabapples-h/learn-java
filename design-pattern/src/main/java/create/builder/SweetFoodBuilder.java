package create.builder;

import create.factory.simplefactory.Food;
import create.factory.simplefactory.FoodFactory;

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
    public Food.Apple builderApple() {
        return FoodFactory.getApple();
    }

    @Override
    public Food.Orange builderOrange() {
        return FoodFactory.getOrange();
    }

    @Override
    public Food.Peach builderPeach() {
        return FoodFactory.getPeach();
    }
}
