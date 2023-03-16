package pattern23.tamplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FuncImpl extends Func {
    @Override
    void func() {
        log.info("FuncImpl : func");
    }
}
