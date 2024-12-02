package structure.adapter._class;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;

@Setter
@Getter
public class PowerV220 {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PowerV220.class);
    private int value = 220;

    public void use220() {
        log.info("正在使用220V电压:[{}]", value);
    }

}
