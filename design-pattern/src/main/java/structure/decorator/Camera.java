package structure.decorator;

import org.slf4j.Logger;

public class Camera {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Camera.class);

    public void func() {
        log.info("拍照");
    }
}
