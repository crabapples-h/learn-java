package doing.template;

import org.slf4j.Logger;

public class FuncImpl extends Func {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FuncImpl.class);

    @Override
    protected void doSomething() {
        log.info("FuncImpl执行了: doSomething()方法");
    }
}
