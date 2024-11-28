package structure.adapter_class;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PowerAdapter extends PowerV220 implements PowerV5 {
    private final static Logger logger = LoggerFactory.getLogger(PowerAdapter.class);

    private PowerV220 v220;

    public PowerAdapter(int value) {
        super(value);
    }

    public PowerAdapter() {
    }

    public PowerAdapter(PowerV220 v220) {
        this.v220 = v220;
    }

    @Override
    public String use5() {
        v220.use220();
        super.setValue(5);
        logger.info("5V电压:[{}]", super.getValue());
        return "5V电压:" + super.getValue();
    }
}
