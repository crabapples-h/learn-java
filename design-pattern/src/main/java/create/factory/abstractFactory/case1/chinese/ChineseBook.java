package create.factory.abstractFactory.case1.chinese;

import create.factory.abstractFactory.case1.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChineseBook implements Book {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void read() {
        logger.info("阅读 :[语文书]");
    }
}