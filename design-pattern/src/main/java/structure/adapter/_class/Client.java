package structure.adapter._class;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 适配器模式(类适配器)
 * 适配器继承220V电压V_220,并实现5V电压V_5接口，适配器修改了220V类中的数据
 * <p>
 * 客户端使用的是V220中的值
 *
 * @author Mr.He
 * 2023/2/25 22:38
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

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
