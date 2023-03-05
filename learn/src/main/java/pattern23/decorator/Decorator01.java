package pattern23.decorator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Decorator01 extends Camera {
    private Camera camera;

    public Decorator01(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void func() {
        log.info("开启美颜");
        camera.func();
    }
}
