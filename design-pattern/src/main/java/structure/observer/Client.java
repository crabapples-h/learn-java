package structure.observer;


import structure.observer.observer.Observer1;
import structure.observer.observer.Observer2;

/**
 * TODO 观察者模式(Observer)
 * 定义了对象之间的一对多依赖，让多个观察者对象同时监听某一个主题对象，当主题对象发生变化时，他的所有依赖着都会收到通知并更新.
 *
 * @author Mr.He
 * 2023/2/27 19:47
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    public static void main(String[] args) {
        // 创建一个对象，并添加观察者
        Obj obj = new Obj();
        obj.addObserver(new Observer1());
        obj.addObserver(new Observer2());
        // 被观察的对象被修改时，观察者会收到通知
        obj.update();
    }
}
