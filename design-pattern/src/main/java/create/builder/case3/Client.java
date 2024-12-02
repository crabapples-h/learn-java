package create.builder.case3;

import create.builder.case3.builder.SweetFoodBuilder;
import create.builder.case3.director.SweetFoodDirector;

/**
 * 建造者模式(使用装配器)-链式调用
 *
 * @author Mr.He
 * 2020/7/13 13:27
 * mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name crabapples
 */
public class Client {
    public static void main(String[] args) {
        // 新建一个建造器，将建造器传给装配器，由装配器调用建造器的方法装配对象
        Food sweetFood = new SweetFoodDirector(new SweetFoodBuilder()).director();
        sweetFood.eat();
    }
}
