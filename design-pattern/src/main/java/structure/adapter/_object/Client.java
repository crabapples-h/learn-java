package structure.adapter._object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO 适配器模式(对象适配器)
 *
 * @author Mr.He
 * 2023/2/26 4:03
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    private final static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        PowerV220 v220 = new PowerV220(220);
        PowerV5 v5 = new PowerAdapter(v220);
        startPower(v5);
    }

    public static void startPower(PowerV5 powerV5) {
        logger.info("手机开始充电:[{}]", powerV5.use5());
    }
}
