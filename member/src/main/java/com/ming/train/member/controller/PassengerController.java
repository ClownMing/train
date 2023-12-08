package com.ming.train.member.controller;

import com.ming.train.common.context.LoginMemberContext;
import com.ming.train.common.resp.CommonResp;
import com.ming.train.member.req.PassengerQueryReq;
import com.ming.train.member.req.PassengerSaveReq;
import com.ming.train.member.resp.PassengerQueryResp;
import com.ming.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author clownMing
 */
@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Resource
    private PassengerService passengerService;

    @PostMapping("/save")
    public CommonResp<Object> save(@Valid @RequestBody PassengerSaveReq req) {
        passengerService.save(req);
        return new CommonResp<>();
    }

    @GetMapping("/query-list")
    public CommonResp<List<PassengerQueryResp>> queryList(@Valid PassengerQueryReq req) {
        req.setMemberId(LoginMemberContext.getId());
        List<PassengerQueryResp> list = passengerService.queryList(req);
        return new CommonResp<>(list);
    }

}
