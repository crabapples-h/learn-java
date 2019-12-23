package Mr.He.pattern23.factory.factorymethod;

/**
 * TODO 工厂模式-工厂方法模式
 *
 * @author Mr.He
 * @date 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class CrabappleFactory implements FlowerFactory {

    @Override
    public Flower createFlower() {
        return new Crabapple();
    }
}
