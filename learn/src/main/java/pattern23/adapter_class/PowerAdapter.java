package pattern23.adapter_class;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class PowerAdapter extends PowerV220 implements PowerV5 {
    private PowerV220 v220;

    public PowerAdapter(PowerV220 v220) {
        this.v220 = v220;
    }

    @Override
    public String use5() {
        v220.use220();
        super.setValue(5);
        log.info("5V电压:[{}]", super.getValue());
        return "5V电压:" + super.getValue();
    }
}
