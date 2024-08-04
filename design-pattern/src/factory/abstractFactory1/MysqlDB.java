package pattern23.factory.abstractFactory1;


/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/27/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class MysqlDB implements DataBase {

    @Override
    public Connect getConnect() {
        return new MysqlConnect();
    }

    @Override
    public Command getCommand() {
        return new MysqlCommand();
    }
}
