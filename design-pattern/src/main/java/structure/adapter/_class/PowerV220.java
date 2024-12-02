package structure.adapter._class;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PowerV220 {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PowerV220.class);
    private int value;

    public void use220() {
        if (value == 0) {
            throw new RuntimeException("电压异常");
        }
        log.info("正在使用220V电压:[{}]", value);
    }

}
