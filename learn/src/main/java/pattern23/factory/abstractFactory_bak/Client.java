package pattern23.factory.abstractFactory_bak;

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
