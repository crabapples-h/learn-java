package create.factory.abstractFactory_bak;


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
        return new Book.ChineseBook();
    }

    static Book buyMathBook() {
        return new Book.MathBook();
    }
}
