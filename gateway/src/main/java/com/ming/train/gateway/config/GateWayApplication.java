package com.ming.train.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author clownMing
 */
@SpringBootApplication
@ComponentScan("com.ming.train.gateway")
public class GateWayApplication {

    private static final Logger LOG = LoggerFactory.getLogger(GateWayApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GateWayApplication.class);
        Environment environment = springApplication.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("gateway地址：\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
