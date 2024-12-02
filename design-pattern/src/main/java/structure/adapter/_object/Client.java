package structure.adapter._object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 适配器模式(对象适配器)
 * 适配器实现5V电压V_5接口，适配器复制了V220中的数据
 * <p>
 * 客户端使用的是适配器中的值
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
        PowerV220 v220 = new PowerV220();
        PowerV5 v5 = new PowerAdapter(v220);
        startPower(v5);
    }

    public static void startPower(PowerV5 powerV5) {
        int power = powerV5.use5();
        if (5 != power) {
            logger.error("充电失败");
        } else {
            logger.info("手机开始充电,电压:[{}]", power);
        }
    }
}
