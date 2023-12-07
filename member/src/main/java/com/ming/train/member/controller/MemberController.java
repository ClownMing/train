package com.ming.train.member.controller;

import com.ming.train.common.resp.CommonResp;
import com.ming.train.member.req.MemberRegisterReq;
import com.ming.train.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author clownMing
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private MemberService memberService;
    @GetMapping("/count")
    public CommonResp<Integer> count() {
        int count = memberService.count();
        return new CommonResp<>(count);
    }
    @PostMapping("/register")
    public CommonResp<Long> register(MemberRegisterReq registerReq) {
        long res = memberService.register(registerReq);
        return new CommonResp<>(res);
    }

}