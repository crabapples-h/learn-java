package doing.tamplate;

/**
 * TODO 模板模式(Template)
 * 定义一个算法骨架，将一些步骤延迟到子类中，模板模式使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤
 * 将某些特定的功能声明为抽象的，交由具体的子类实现。但调用时是由其父类进行调用的
 *
 * @author Mr.He
 * 2023/2/27 18:12
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    public static void main(String[] args) {
         Func func = new FuncImpl();
         func.run();
    }
}
