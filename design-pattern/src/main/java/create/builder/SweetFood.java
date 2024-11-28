package create.builder;

import create.factory.simplefactory.Food;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class SweetFood implements Food {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Food.Apple apple;
    private Food.Orange orange;
    private Food.Peach peach;

    @Override
    public void eat() {
        logger.info("eat:[sweetFood]");
    }
}
