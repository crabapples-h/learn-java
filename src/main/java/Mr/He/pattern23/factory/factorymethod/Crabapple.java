package Mr.He.pattern23.factory.factorymethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 工厂模式-工厂方法模式
 *
 * @author Mr.He
 * @date 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Crabapple implements Flower {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void show(){
        logger.info("这是海棠:{}",this);
    }
}
