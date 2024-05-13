package pattern23.adapter_class;

import org.slf4j.Logger;

public class PowerV220 {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PowerV220.class);
    private int value;

    public PowerV220(int value) {
        this.value = value;
    }

    public PowerV220() {
    }

    public String use220() {
        if (value == 0) {
            throw new RuntimeException("电压异常");
        }
        log.info("220V电压:[{}]", value);
        return "220V电压:" + value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
