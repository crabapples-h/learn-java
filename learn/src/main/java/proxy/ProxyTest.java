package proxy;

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        Food food = (Food)Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), new Class[]{Food.class},
                (proxy, method, args1) -> {
                    System.err.println(method);
                    System.err.println(args1);
//                    method.invoke(proxy, args1);
                    System.out.println("吃东西");
                    return "吃东西";
                });
        String eat = food.eat();
        System.err.println(eat);
    }
}
