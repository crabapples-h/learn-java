package structure.adapter._object;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public class PowerV220 {
    private final static Logger logger = LoggerFactory.getLogger(PowerV220.class);
    private final int value = 220;

    public void use220() {
        logger.info("220V电压:[{}]", value);
    }
}
