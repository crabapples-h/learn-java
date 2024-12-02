package create.factory.abstractFactory.case1.factory;


import create.factory.abstractFactory.case1.Book;
import create.factory.abstractFactory.case1.chinese.ChineseBook;
import create.factory.abstractFactory.case1.math.MathBook;

/**
 * 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class BookFactory {
    public static Book buyChineseBook() {
        return new ChineseBook();
    }

    public static Book buyMathBook() {
        return new MathBook();
    }
}
