package pattern23.adapter_interface;

import org.slf4j.Logger;

/**
 * TODO 适配器模式(接口适配器)
 *
 * @author Mr.He
 * 2023/2/26 4:03
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mrhe
 */
public class Client {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        addListener(new AdapterListener() {
            @Override
            public void onStart() {
                log.info("回调:[{}]", "onStart");
            }
        });
        addListener(new AdapterListener() {
            @Override
            public void onPause() {
                log.info("回调:[{}]", "onPause");
            }
        });
    }

    public static void addListener(Listener listener) {
        log.info("监听器实现:[{}]", listener);
        listener.onStart();
        listener.onPause();
        listener.onResume();
        listener.onStop();
    }
}
