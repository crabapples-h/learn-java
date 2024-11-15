package structure.adapter_obj;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PowerAdapter implements PowerV5 {
    private final static Logger logger = LoggerFactory.getLogger(PowerAdapter.class);

    private PowerV220 v220;
    private int value;

    public PowerAdapter(PowerV220 v220) {
        this.v220 = v220;
    }

    @Override
    public String use5() {
        String s = v220.use220();
        this.value = 5;
        logger.info("5V电压:[{}]", value);
        return "5V电压:" + value;
    }
}
