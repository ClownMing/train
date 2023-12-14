package com.ming.train.business.feign;

import com.ming.train.common.req.MemberTicketReq;
import com.ming.train.common.resp.CommonResp;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author clownMing
 */
@FeignClient("member")
//@FeignClient(name = "member", url = "http://127.0.0.1:8001")
public interface MemberFeign {

    @PostMapping("/member/member/feign/ticket/save")
    CommonResp<Object> save(@Valid @RequestBody MemberTicketReq req);

}
