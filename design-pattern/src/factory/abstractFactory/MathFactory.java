package factory.abstractFactory;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/27/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class MathFactory {
    static Book buyBook() {
        return BookFactory.buyMathBook();
    }

    static Pen buyPen() {
        return PenFactory.buyMathPen();
    }
}
