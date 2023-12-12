package com.ming.train.business.controller.admin;

import com.ming.train.business.req.ConfirmOrderQueryReq;
import com.ming.train.business.resp.ConfirmOrderQueryResp;
import com.ming.train.business.service.ConfirmOrderService;
import com.ming.train.common.resp.CommonResp;
import com.ming.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/confirm-order")
public class ConfirmOrderAdminController {

    @Resource
    private ConfirmOrderService confirmOrderService;

    @GetMapping("/query-list")
    public CommonResp<PageResp<ConfirmOrderQueryResp>> queryList(@Valid ConfirmOrderQueryReq req) {
        PageResp<ConfirmOrderQueryResp> list = confirmOrderService.queryList(req);
        return new CommonResp<>(list);
    }

}
