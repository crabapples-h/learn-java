package pattern23.strategy;

import lombok.extern.slf4j.Slf4j;

public interface Interest {
    void show();
}

@Slf4j
class Eat implements Interest {
    @Override
    public void show() {
        log.info("eat()");
    }
}

@Slf4j
class Sing implements Interest {
    @Override
    public void show() {
        log.info("sing()");
    }
}
