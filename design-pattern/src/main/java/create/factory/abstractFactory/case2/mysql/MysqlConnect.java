package create.factory.abstractFactory.case2.mysql;


import create.factory.abstractFactory.case2.Connect;
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
public class MysqlConnect implements Connect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void connect() {
        logger.info("mysql:[{}]", "开始连接");
    }
}
