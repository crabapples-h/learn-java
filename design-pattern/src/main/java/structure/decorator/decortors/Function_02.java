package structure.decorator.decortors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import structure.decorator.Camera;

public class Function_02 extends Camera {
    private static final Logger log = LoggerFactory.getLogger(Function_02.class);
    private final Camera camera;

    public Function_02(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void func() {
        log.info("开启滤镜");
        camera.func();
    }
}
