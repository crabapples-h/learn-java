package Mr.He.pattern23.factory.abstractFactory;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * @date 12/26/19
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
        mathBook.read();
        chinesePen.write();
        mathPen.write();
    }

}
