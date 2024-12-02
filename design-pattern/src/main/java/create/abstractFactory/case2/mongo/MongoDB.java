package create.abstractFactory.case2.mongo;


import create.abstractFactory.case2.Command;
import create.abstractFactory.case2.Connect;
import create.abstractFactory.case2.DataBase;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/27/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class MongoDB implements DataBase {

    @Override
    public Connect getConnect() {
        return new MongoConnect();
    }

    @Override
    public Command getCommand() {
        return new MongoCommand();
    }
}
