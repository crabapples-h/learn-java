package structure.adapter._object;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PowerAdapter implements PowerV5 {
    private final static Logger logger = LoggerFactory.getLogger(PowerAdapter.class);

    private final PowerV220 v220;
    private int value;

    public PowerAdapter(PowerV220 v220) {
        this.v220 = v220;
    }

    @Override
    public int use5() {
        v220.use220();
        logger.info("正在转换电压");
        this.value = 5;
        return this.value;
    }
}
