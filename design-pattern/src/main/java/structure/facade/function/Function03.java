package structure.facade.function;

import org.slf4j.Logger;

/**
 * TODO 门面模式
 *
 * @author Mr.He
 * 2023/2/25 18:47
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Function03 {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Function03.class);

    public void func() {
        log.info("Function03执行了[{}]", this);
    }
}
