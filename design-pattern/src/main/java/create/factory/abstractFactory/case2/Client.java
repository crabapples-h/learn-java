package create.factory.abstractFactory.case2;

import create.factory.abstractFactory.case2.mongo.MongoDB;
import create.factory.abstractFactory.case2.mysql.MysqlDB;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
// 抽象工厂类型二
public class Client {
    public static void main(String[] args) {
        DataBase mysqlDB = new MysqlDB();
        Connect mysqlConnect = mysqlDB.getConnect();
        Command mysqlCommand = mysqlDB.getCommand();
        mysqlConnect.connect();
        mysqlCommand.command();

        DataBase mongoDB = new MongoDB();
        Connect mongoConnect = mongoDB.getConnect();
        Command mongoCommand = mongoDB.getCommand();
        mongoConnect.connect();
        mongoCommand.command();
    }
}
