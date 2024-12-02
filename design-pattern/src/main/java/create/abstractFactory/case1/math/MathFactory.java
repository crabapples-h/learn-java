package create.abstractFactory.case1.math;

import create.abstractFactory.case1.Book;
import create.abstractFactory.case1.Pen;
import create.abstractFactory.case1.factory.BookFactory;
import create.abstractFactory.case1.factory.PenFactory;

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
    public static Book buyBook() {
        return BookFactory.buyMathBook();
    }

    public static Pen buyPen() {
        return PenFactory.buyMathPen();
    }
}
