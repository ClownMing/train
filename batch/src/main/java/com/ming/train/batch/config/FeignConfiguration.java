package com.ming.train.batch.config;

import com.ming.train.batch.feign.BusinessFeignFallbackFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author clownMing
 */
@Configuration
public class FeignConfiguration {


    @Bean
    public BusinessFeignFallbackFactory businessFeignFallbackFactory() {
        return new BusinessFeignFallbackFactory();
    }

}
