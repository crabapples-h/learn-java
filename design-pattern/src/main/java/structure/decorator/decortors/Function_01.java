package structure.decorator.decortors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import structure.decorator.Camera;

public class Function_01 extends Camera {
    private final static Logger logger = LoggerFactory.getLogger(Function_01.class);

    private final Camera camera;

    public Function_01(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void func() {
        logger.info("开启美颜");
        camera.func();
    }
}
