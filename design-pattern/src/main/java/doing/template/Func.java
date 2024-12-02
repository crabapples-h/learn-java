package doing.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Func {

    public void run() {
        log.info("Func执行了: before()方法");
        doSomething();
        log.info("Func执行了: end()方法");
    }

    abstract void doSomething();
}
