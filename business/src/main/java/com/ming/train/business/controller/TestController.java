package com.ming.train.business.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clownMing
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String testHello() {
        return "business test hello";
    }
}
