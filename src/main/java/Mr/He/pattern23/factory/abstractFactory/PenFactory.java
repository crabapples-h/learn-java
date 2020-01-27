package Mr.He.pattern23.factory.abstractFactory;

/**
 * TODO 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class PenFactory {
    static Pen buyChinesePen(){
        return new Pen.ChinesePen();
    }
    static Pen buyMathPen() {
        return new Pen.MathPen();
    }
}
