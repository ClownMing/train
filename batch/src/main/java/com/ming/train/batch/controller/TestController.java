package com.ming.train.batch.controller;

import com.ming.train.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clownMing
 */
@RestController
public class TestController {

    @Resource
    BusinessFeign businessFeign;

    @GetMapping("/hello")
    public String testHello() {
        String businessHello = businessFeign.testHello();
        return "batch test hello >>> businessFeign result---->" + businessHello;
    }
}
