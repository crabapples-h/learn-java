package builder;


import factory.simplefactory.Apple;
import factory.simplefactory.FoodFactory;

/**
 * TODO 建造者模式-装配器(实现类)
 *
 * @author Mr.He
 * 2020/7/13 13:30
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class SweetFoodDirector implements FoodDirector {
    private final SweetFoodBuilder sweetFoodBuilder;

    public SweetFoodDirector(SweetFoodBuilder sweetFoodBuilder) {
        this.sweetFoodBuilder = sweetFoodBuilder;
    }

    public SweetFood director() {
        SweetFood sweetFood = new SweetFood();
        Apple apple = sweetFoodBuilder.builderApple();
        FoodFactory.Orange orange = sweetFoodBuilder.builderOrange();
        FoodFactory.Peach peach = sweetFoodBuilder.builderPeach();
        sweetFood.setApple(apple);
        sweetFood.setOrange(orange);
        sweetFood.setPeach(peach);
        return sweetFood;
    }
}
