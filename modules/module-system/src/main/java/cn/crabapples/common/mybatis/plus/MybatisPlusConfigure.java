package cn.crabapples.common.mybatis.plus;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfigure implements ConfigurationCustomizer {


    @Override
    public void customize(MybatisConfiguration configuration) {
    }
}
