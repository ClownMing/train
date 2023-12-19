package com.ming.train.batch.feign;

import com.ming.train.common.resp.CommonResp;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Date;

/**
 * @author clownMing
 */
public class BusinessFeignFallbackFactory implements FallbackFactory<BusinessFeign> {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessFeignFallbackFactory.class);
    @Override
    public BusinessFeign create(Throwable cause) {
        return new BusinessFeign() {
            @Override
            public CommonResp<Object> genDaily(Date date) {
                LOG.error(()-> "batch调用feign的genDaily方法出错啦 ->> {}", cause);
                CommonResp<Object> objectCommonResp = new CommonResp<>();
                objectCommonResp.setSuccess(false);
                return objectCommonResp;
            }

            @Override
            public String testHello() {
                LOG.error(()-> "batch调用feign的testHello方法出错啦 ->>{}", cause);
                return null;
            }
        };
    }
}
