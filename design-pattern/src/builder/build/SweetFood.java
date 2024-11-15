package builder.build;

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
    private Apple apple;
    private FoodFactory.Orange orange;
    private FoodFactory.Peach peach;

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public FoodFactory.Orange getOrange() {
        return orange;
    }

    public void setOrange(FoodFactory.Orange orange) {
        this.orange = orange;
    }

    public FoodFactory.Peach getPeach() {
        return peach;
    }

    public void setPeach(FoodFactory.Peach peach) {
        this.peach = peach;
    }

    @Override
    public void eat() {
        logger.info("eat:[{}][{}][{}]", apple, orange, peach);
    }
}
