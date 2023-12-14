package com.ming.train.member.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clownMing
 */
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {


    @Value("${test.nacos}")
    private String nacos;

    @GetMapping("/hello")
    public String testNacos() {
        return "get the value >>>" + nacos;
    }
}
