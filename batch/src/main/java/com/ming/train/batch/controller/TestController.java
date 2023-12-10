package com.ming.train.batch.controller;

import com.ming.train.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clownMing
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Resource
    BusinessFeign businessFeign;

    @GetMapping("/hello")
    public void hello() {
        String businessRes = businessFeign.testHello("feign");
        LOG.info("feign call result >>> {}", businessRes);
    }
}
