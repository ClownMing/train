package com.ming.train.member.controller;

import com.ming.train.common.resp.CommonResp;
import com.ming.train.member.req.MemberLoginReq;
import com.ming.train.member.req.MemberRegisterReq;
import com.ming.train.member.req.MemberSendCodeReq;
import com.ming.train.member.resp.MemberLoginResp;
import com.ming.train.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    public CommonResp<Long> register(@Valid MemberRegisterReq registerReq) {
        long res = memberService.register(registerReq);
        return new CommonResp<>(res);
    }

    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid @RequestBody MemberSendCodeReq memberSendCodeReq) {
        memberService.sendCode(memberSendCodeReq);
        return new CommonResp<>();
    }

    @PostMapping("/login")
    public CommonResp<MemberLoginResp> login(@Valid @RequestBody MemberLoginReq memberLoginReq) {
        MemberLoginResp res = memberService.login(memberLoginReq);
        return new CommonResp<>(res);
    }

}
