package create.builder.director.case2.director;


import create.builder.director.case2.Food;
import create.builder.director.case2.SweetFood;
import create.builder.director.case2.builder.SweetFoodBuilder;
import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;

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

    public Food director() {
        SweetFood sweetFood = new SweetFood();
        Apple apple = sweetFoodBuilder.builderApple();
        Orange orange = sweetFoodBuilder.builderOrange();
        Peach peach = sweetFoodBuilder.builderPeach();
        sweetFood.setApple(apple);
        sweetFood.setOrange(orange);
        sweetFood.setPeach(peach);
        return sweetFood;
    }
}
