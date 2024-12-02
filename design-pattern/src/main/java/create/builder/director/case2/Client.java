package create.builder.director.case2;

import create.builder.director.case2.builder.SweetFoodBuilder;
import create.builder.director.case2.director.SweetFoodDirector;

/**
 * 建造者模式-链式调用
 *
 * @author Mr.He
 * 2020/7/13 13:27
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class Client {
    public static void main(String[] args) {
        Food sweetFood = new SweetFoodDirector(new SweetFoodBuilder()).director();
        sweetFood.eat();
    }
}
