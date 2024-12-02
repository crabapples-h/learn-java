package create.factory.factorymethod.crabapples;

import create.factory.factorymethod.Flower;
import create.factory.factorymethod.FlowerFactory;

/**
 * 海棠工厂
 */
public class CrabapplesFactory implements FlowerFactory {
    @Override
    public Flower createFlower() {
        return new Crabapples();
    }

}