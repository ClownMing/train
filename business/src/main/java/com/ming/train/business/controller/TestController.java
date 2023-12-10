package com.ming.train.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clownMing
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String testHello(String param) {
        String res = param + " business hello";
        return res;
    }
}
