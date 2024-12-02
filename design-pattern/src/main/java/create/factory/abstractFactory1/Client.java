package create.factory.abstractFactory1;

import create.factory.abstractFactory1.mysql.MysqlDB;

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
        Command command = mysqlDB.getCommand();
        connect.connect();
        command.command();
    }
}
