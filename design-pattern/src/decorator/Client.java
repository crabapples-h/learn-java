package decorator;

/**
 * TODO 装饰器模式
 *
 * @author Mr.He
 * 2023/2/26 4:04
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    public static void main(String[] args) {
        Camera camera = new Decorator02(new Decorator01(new Camera()));
        click(camera);
    }

    static void click(Camera camera) {
        camera.func();
    }
}
