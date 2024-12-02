package create.factory.factorymethod;

import create.factory.factorymethod.crabapples.CrabapplesFactory;
import create.factory.factorymethod.rose.RoseFactory;

/**
 * 工厂模式-工厂方法模式
 * 将具体产品和创建者解耦
 * 拓展时实现工厂接口和对象接口即可
 *
 * @author Mr.He
 * 12/20/19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name root
 */
public class Client {
    public static void main(String[] args) {
        // 创建两个工厂
        FlowerFactory crabappleFactory = new CrabapplesFactory();
        FlowerFactory roseFactory = new RoseFactory();
        // 调用工厂的create方法可以获取不同类型对象
        Flower crabapple = crabappleFactory.createFlower();
        Flower rose = roseFactory.createFlower();
        crabapple.show();
        rose.show();
    }
}
