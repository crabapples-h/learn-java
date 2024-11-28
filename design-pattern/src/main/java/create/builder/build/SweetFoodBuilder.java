package create.builder.build;


import create.factory.simplefactory.Apple;
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
    private SweetFood food = new SweetFood();

    @Override
    public SweetFoodBuilder builderApple(Apple apple) {
        food.setApple(apple);
        return this;
    }

    @Override
    public SweetFoodBuilder builderOrange(FoodFactory.Orange orange) {
        food.setOrange(orange);
        return this;
    }

    @Override
    public SweetFoodBuilder builderPeach(FoodFactory.Peach peach) {
        food.setPeach(peach);
        return this;
    }

    @Override
    public SweetFood build() {
        return food;
    }

}
