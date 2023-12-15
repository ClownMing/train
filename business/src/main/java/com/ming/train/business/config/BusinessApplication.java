package com.ming.train.business.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author clownMing
 */
@SpringBootApplication
@ComponentScan("com.ming.train")
@MapperScan("com.ming.train.*.mapper")
@EnableFeignClients("com.ming.train.business.feign")
// 开启SpringBoot内置缓存
@EnableCaching
public class BusinessApplication {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BusinessApplication.class);
        Environment environment = app.run(args).getEnvironment();
        LOG.info("启动成功!!");
        LOG.info("地址：\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
