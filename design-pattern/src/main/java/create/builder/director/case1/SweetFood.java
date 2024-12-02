package create.builder.director.case1;

import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;

/**
 * TODO 建造者模式-实体类
 *
 * @author Mr.He
 * 2020/7/13 13:29
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class SweetFood implements Food {
    private final Apple apple;
    private final Orange orange;
    private final Peach peach;

    public SweetFood(Apple apple, Orange orange, Peach peach) {
        this.apple = apple;
        this.orange = orange;
        this.peach = peach;
    }

    @Override
    public void eat() {
        apple.eat();
        orange.eat();
        peach.eat();
    }
}
