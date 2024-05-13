package pattern23.decorator;

import org.slf4j.Logger;

public class Decorator02 extends Camera {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Decorator02.class);
    Camera camera;

    public Decorator02(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void func() {
        log.info("开启滤镜");
        camera.func();
    }
}
