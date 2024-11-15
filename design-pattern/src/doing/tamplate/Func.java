package doing.tamplate;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public abstract class Func {
    private final static Logger logger = LoggerFactory.getLogger(Func.class);

    public void run() {
        logger.info("before");
        func();
        logger.info("end");
    }

    abstract void func();
}
