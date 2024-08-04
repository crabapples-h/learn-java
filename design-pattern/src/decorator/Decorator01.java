package decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Decorator01 extends Camera {
    private final static Logger logger = LoggerFactory.getLogger(Decorator01.class);

    private Camera camera;

    public Decorator01(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void func() {
        logger.info("开启美颜");
        camera.func();
    }
}
