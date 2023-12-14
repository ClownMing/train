package com.ming.train.batch.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clownMing
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${test.nacos}")
    private String nacos;

    @GetMapping("/hello")
    public String testHello() {
        return "batch nacos get success >>> " + nacos;
    }
}
