package pattern23.adapter_class;

import org.slf4j.Logger;

/**
 * TODO 适配器模式(类适配器)
 *
 * @author Mr.He
 * 2023/2/25 22:38
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        PowerV220 v220 = new PowerV220(220);
        PowerV5 v5 = new PowerAdapter(v220);
        phone(v5);
    }

    public static void phone(PowerV5 powerV5) {
        log.info("手机开始充电:[{}]", powerV5.use5());
    }
}
