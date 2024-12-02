package create.factory.abstractFactory.chinese;

import create.factory.abstractFactory.Book;
import create.factory.abstractFactory.BookFactory;
import create.factory.abstractFactory.Pen;
import create.factory.abstractFactory.PenFactory;

/**
 * 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/27/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class ChineseFactory {
    public static Book buyBook() {
        return BookFactory.buyChineseBook();
    }

    public static Pen buyPen() {
        return PenFactory.buyChinesePen();
    }
}
