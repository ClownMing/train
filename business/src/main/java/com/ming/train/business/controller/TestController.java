package com.ming.train.business.controller;

import com.ming.train.common.resp.CommonResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clownMing
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/hello")
    public CommonResp<String> testHello() {
        return new CommonResp<>("success");
    }

}
