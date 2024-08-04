package facade;

/**
 * TODO 门面模式
 *
 * @author Mr.He
 * 2023/2/25 18:47
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Facade {
    public void func() {
         Function01 function01 = new Function01();
         Function02 function02 = new Function02();
         Function03 function03 = new Function03();
         function01.func();
         function02.func();
         function03.func();
    }
}
