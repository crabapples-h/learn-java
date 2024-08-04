package pattern23.builder.director;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pattern23.factory.simplefactory.Apple;
import pattern23.factory.simplefactory.FoodFactory.*;

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
    private final Orange orange;
    private final Peach peach;

    public SweetFood(Apple apple, Orange orange, Peach peach) {
        this.apple = apple;
        this.orange = orange;
        this.peach = peach;
    }

    @Override
    public void eat() {
        logger.info("eat:[{}][{}][{}]", apple, orange, peach);
    }
}
