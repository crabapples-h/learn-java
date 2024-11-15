package cn.crabapples.turing;

import cn.crabapple.turing.TuringApiProperties;
import cn.crabapple.turing.TuringApiUtils;
import org.junit.Test;

public class TuringTest {
    @Test
    public  void case1() {
        TuringApiUtils turingApiUtils = new TuringApiUtils(new TuringApiProperties());
        String s = turingApiUtils.sendToTuringApi("你好");
        System.err.println(s);
    }
}
