package create.factory.abstractFactory;

import create.factory.abstractFactory.chinese.ChineseFactory;
import create.factory.abstractFactory.math.MathFactory;

/**
 * 工厂模式-抽象工厂
 * 提供一个创建一系列相关或互相依赖对象的接口，而无需指定他们具体的类
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client {
    public static void main(String[] args) {
        Book chineseBook = ChineseFactory.buyBook();
        Book mathBook = MathFactory.buyBook();

        Pen chinesePen = ChineseFactory.buyPen();
        Pen mathPen = MathFactory.buyPen();

        chineseBook.read();
        chinesePen.write();
        mathBook.read();
        mathPen.write();
    }

}
