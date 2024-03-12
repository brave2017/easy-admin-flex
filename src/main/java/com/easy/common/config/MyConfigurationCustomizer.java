package com.easy.common.config;


import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.context.annotation.Configuration;

/**
 * @author Maw
 */
@Configuration
public class MyConfigurationCustomizer implements ConfigurationCustomizer {
    @Override
    public void customize(FlexConfiguration configuration) {
        // sql 日志
//        configuration.setLogImpl(StdOutImpl.class);
    }
}
