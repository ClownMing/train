package com.ming.train.member.controller;

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


    @GetMapping("/hello")
    public String testNacos() {
        return "<<< hello test >>>";
    }
}
