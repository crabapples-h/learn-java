package builder;

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

    private Apple apple;
    private Orange orange;
    private Peach peach;

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public Orange getOrange() {
        return orange;
    }

    public void setOrange(Orange orange) {
        this.orange = orange;
    }

    public Peach getPeach() {
        return peach;
    }

    public void setPeach(Peach peach) {
        this.peach = peach;
    }

    @Override
    public void eat() {
        logger.info("eat:[sweetFood]");
    }
}
