package pattern23.adapter_obj;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PowerAdapter implements PowerV5 {
    private PowerV220 v220;
    private int value;

    public PowerAdapter(PowerV220 v220) {
        this.v220 = v220;
    }

    @Override
    public String use5() {
        v220.use220();
        this.value = 5;
        log.info("5V电压:[{}]", value);
        return "5V电压:" + value;
    }
}
