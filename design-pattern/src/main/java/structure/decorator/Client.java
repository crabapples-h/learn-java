package structure.decorator;

import structure.decorator.decortors.Function_01;
import structure.decorator.decortors.Function_02;

/**
 * 装饰器模式
 * 在不改变原有对象的基础上，将新的功能附加到对象上
 *
 * @author Mr.He
 * 2023/2/26 4:04
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    public static void main(String[] args) {
        Camera camera = new Function_02(new Function_01(new Camera()));
        camera.func();
    }
}
