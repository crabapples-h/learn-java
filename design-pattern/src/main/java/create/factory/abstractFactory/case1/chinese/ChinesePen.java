package create.factory.abstractFactory.case1.chinese;

import create.factory.abstractFactory.case1.Pen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChinesePen implements Pen {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write() {
        logger.info("书写 :[语文]");
    }
}