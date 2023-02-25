package pattern23.adapter_obj;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Client {
    public static void main(String[] args) {
        V_220 source = new V_220();
        Adapter adapter = new PhoneAdapter();
        V_5 v5 = adapter.adapter(source);
        phone(v5);
    }

    public static void phone(V_5 v5) {
        log.info("手机开始充电:[{}]", v5);
    }
}
