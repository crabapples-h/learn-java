package structure.adapter._class;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class PowerAdapter extends PowerV220 implements PowerV5 {
    private final static Logger logger = LoggerFactory.getLogger(PowerAdapter.class);

    private final PowerV220 v220;

    @Override
    public String use5() {
        v220.use220();
        logger.info("正在转换电压");
        super.setValue(5);
        logger.info("当前电压:[{}]", super.getValue());
        return "5V电压:" + super.getValue();
    }
}
