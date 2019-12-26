package Mr.He.pattern23.factory.abstractFactory;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * @date 12/27/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class ChineseFactory {
    static Book buyBook(){
        return BookFactory.buyChineseBook();
    }
    static Pen buyPen(){
        return PenFactory.buyChinesePen();
    }
}
