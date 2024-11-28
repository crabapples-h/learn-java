package doing.tamplate;

import org.slf4j.Logger;

public class FuncImpl extends Func {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FuncImpl.class);

    @Override
    void func() {
        log.info("FuncImpl : func");
    }
}
