package Mr.He.pattern23.factory.simplefactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 工厂模式-简单工厂模式(静态工厂模式)
 *
 * @author Mr.He
 * @date 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Peach implements Food{
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void eat(){
        logger.info("吃东西:{}",this);
    }
}
