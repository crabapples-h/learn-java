package builder.director;


import factory.simplefactory.Apple;
import factory.simplefactory.FoodFactory;

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
    private Apple apple;
    private FoodFactory.Orange orange;
    private FoodFactory.Peach peach;

    @Override
    public void builderApple(Apple apple) {
        this.apple = apple;
    }

    @Override
    public void builderOrange(FoodFactory.Orange orange) {
        this.orange = orange;
    }

    @Override
    public void builderPeach(FoodFactory.Peach peach) {
        this.peach = peach;
    }

    @Override
    public SweetFood build() {
        return new SweetFood(apple, orange, peach);
    }

}
