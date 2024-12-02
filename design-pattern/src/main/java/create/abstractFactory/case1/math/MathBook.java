package create.abstractFactory.case1.math;

import create.abstractFactory.case1.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathBook implements Book {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void read() {
        logger.info("阅读 :[数学书]");
    }
}