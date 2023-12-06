package com.ming.train.member.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

/**
 * @author clownMing
 */
@SpringBootApplication
@ComponentScan("com.ming")
@EnableAspectJAutoProxy
public class MemberApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class.getName());
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MemberApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功!!");
        LOG.info("member地址：\thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }
}
