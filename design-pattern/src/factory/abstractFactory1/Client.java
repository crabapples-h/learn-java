package pattern23.factory.abstractFactory1;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client {
    public static void main(String[] args) {
        DataBase mysqlDB = new MysqlDB();
        Connect connect = mysqlDB.getConnect();
        connect.connect();
        Command command = mysqlDB.getCommand();
        command.command();
    }
}
