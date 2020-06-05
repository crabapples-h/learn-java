package pattern23.factory.factorymethod;

/**
 * TODO 工厂模式-工厂方法模式
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client {
    public static void main(String[] args) {
        Flower flower = new CrabappleFactory().createFlower();
        Flower rose = new RoseFactory().createFlower();
        flower.show();
        rose.show();
    }
}
