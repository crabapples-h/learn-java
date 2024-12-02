package create.abstractFactory.case1;

import create.abstractFactory.case1.chinese.ChineseFactory;
import create.abstractFactory.case1.math.MathFactory;

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
// 抽象工厂类型一
public class Client {
    public static void main(String[] args) {
        Book chineseBook = ChineseFactory.buyBook();
        Pen chinesePen = ChineseFactory.buyPen();

        Book mathBook = MathFactory.buyBook();
        Pen mathPen = MathFactory.buyPen();

        chineseBook.read();
        chinesePen.write();
        mathBook.read();
        mathPen.write();
    }

}
