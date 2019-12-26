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
public class BookFactory {
    static Book buyChineseBook (){
        return new Book.ChineseBook();
    }
    static Book buyMathBook (){
        return new Book.MathBook();
    }
}
