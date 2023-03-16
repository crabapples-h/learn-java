package pattern23.tamplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Func {
    public void run() {
        log.info("before");
        func();
        log.info("end");
    }

    abstract void func();
}
