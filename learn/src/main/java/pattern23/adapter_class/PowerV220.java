package pattern23.adapter_class;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PowerV220 {
    private int value;

    public String use220() {
        if (value == 0) {
            throw new RuntimeException("电压异常");
        }
        log.info("220V电压:[{}]", value);
        return "220V电压:" + value;
    }
}
