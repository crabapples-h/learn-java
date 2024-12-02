package create.builder.case2.director;


import create.builder.case2.SweetFood;
import create.builder.case2.builder.SweetFoodBuilder;
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
    private final SweetFoodBuilder builder;

    public SweetFoodDirector(SweetFoodBuilder builder) {
        this.builder = builder;
    }

    public SweetFood director(Apple apple, Orange orange, Peach peach) {
        builder.builderApple(apple);
        builder.builderOrange(orange);
        builder.builderPeach(peach);
        return builder.build();
    }

}
