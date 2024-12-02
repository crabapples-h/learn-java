package structure.adapter._object;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class PowerV220 {
    private final static Logger logger = LoggerFactory.getLogger(PowerV220.class);

    private int value;

    public void use220() {
        if (0 == value) {
            throw new RuntimeException("电压异常");
        }
        logger.info("220V电压:[{}]", value);
    }

    public PowerV220(int value) {
        this.value = value;
    }
}
