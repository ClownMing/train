package com.ming.train.batch.feign;

import com.ming.train.common.resp.CommonResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

/**
 * @author clownMing
 */
@FeignClient(value = "business", fallbackFactory = BusinessFeignFallbackFactory.class)
//@FeignClient(name = "business", url = "http://localhost:8002/business")
public interface BusinessFeign {

    @GetMapping("/business/admin/daily-train/gen-daily/{date}")
    CommonResp<Object> genDaily(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date);

    @GetMapping("business/hello")
    String testHello();


}
