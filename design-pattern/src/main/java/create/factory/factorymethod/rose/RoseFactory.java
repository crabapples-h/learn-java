package create.factory.factorymethod.rose;

import create.factory.factorymethod.Flower;
import create.factory.factorymethod.FlowerFactory;

public class RoseFactory implements FlowerFactory {
    @Override
    public Flower createFlower() {
        return new Rose();
    }
}