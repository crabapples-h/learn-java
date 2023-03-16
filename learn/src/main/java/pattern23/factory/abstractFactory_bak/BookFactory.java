package pattern23.factory.abstractFactory_bak;

import pattern23.factory.abstractFactory_bak.Book.*;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class BookFactory {
    static Book buyChineseBook() {
        return new ChineseBook();
    }

    static Book buyMathBook() {
        return new MathBook();
    }
}
