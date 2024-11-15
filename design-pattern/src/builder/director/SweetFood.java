package builder.director;

import factory.simplefactory.Apple;
import factory.simplefactory.FoodFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Apple apple;
    private final FoodFactory.Orange orange;
    private final FoodFactory.Peach peach;

    public SweetFood(Apple apple, FoodFactory.Orange orange, FoodFactory.Peach peach) {
        this.apple = apple;
        this.orange = orange;
        this.peach = peach;
    }

    @Override
    public void eat() {
        logger.info("eat:[{}][{}][{}]", apple, orange, peach);
    }
}
