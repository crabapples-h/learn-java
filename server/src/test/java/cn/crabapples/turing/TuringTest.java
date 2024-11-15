package cn.crabapples.turing;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TuringTest {
    @Autowired
    TuringApiService apiService;

    @Test
    public void case1() {
        System.out.println(apiService);
        String s = apiService.sendMessage("你知道chatgpt吗", "001", 0);
        System.err.println(s);
    }
}
