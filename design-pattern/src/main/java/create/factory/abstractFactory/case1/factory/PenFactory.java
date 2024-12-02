package create.factory.abstractFactory.case1.factory;


import create.factory.abstractFactory.case1.Pen;
import create.factory.abstractFactory.case1.chinese.ChinesePen;
import create.factory.abstractFactory.case1.math.MathPen;

/**
 * 工厂模式-抽象工厂
 *
 * @author Mr.He
 * 12/26/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class PenFactory {
    public static Pen buyChinesePen() {
        return new ChinesePen();
    }

    public static Pen buyMathPen() {
        return new MathPen();
    }
}
