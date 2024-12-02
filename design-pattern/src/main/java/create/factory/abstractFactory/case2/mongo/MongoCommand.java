package create.factory.abstractFactory.case2.mongo;


import create.factory.abstractFactory.case2.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class MongoCommand implements Command {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void command() {
        logger.info("Mongo:[{}]", "执行命令");

    }
}
