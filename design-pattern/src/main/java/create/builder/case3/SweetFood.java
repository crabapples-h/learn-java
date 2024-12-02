package create.builder.case3;

import create.factory.simplefactory.food.Apple;
import create.factory.simplefactory.food.Orange;
import create.factory.simplefactory.food.Peach;
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

    private Apple apple;
    private Orange orange;
    private Peach peach;

    @Override
    public void eat() {
        apple.eat();
        orange.eat();
        peach.eat();
    }
}
