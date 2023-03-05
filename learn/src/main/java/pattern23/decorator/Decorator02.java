package pattern23.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Decorator02 extends Camera {
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
