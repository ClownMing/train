package com.ming.train.business.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.ming.train.business.enums.RedisKeyPreEnum;
import com.ming.train.business.mapper.ConfirmOrderMapper;
import com.ming.train.business.req.ConfirmOrderDoReq;
import com.ming.train.common.exception.BusinessException;
import com.ming.train.common.exception.BusinessExceptionEnum;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author clownMing
 */
@Service
public class BeforeConfirmOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(BeforeConfirmOrderService.class);

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Resource
    private SkTokenService skTokenService;

    @Resource
    private ConfirmOrderService confirmOrderService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @SentinelResource(value = "beforeDoConfirm", blockHandler = "beforeDoConfirmBlock")
    public void beforeDoConfirm(ConfirmOrderDoReq req) {
        // 校验令牌余量(能不能抢到锁且购买的票是否充足)
        boolean validSkToken = skTokenService.validSkToken(req.getDate(), req.getTrainCode(),req.getMemberId());
        if(validSkToken) {
            LOG.info("令牌校验通过");
        } else {
            LOG.info("令牌校验不通过");
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_SK_TOKEN_FAIL);
        }
        // 获取车次锁
        String lockKey = RedisKeyPreEnum.CONFIRM_ORDER + "-" + DateUtil.formatDate(req.getDate()) + "-" + req.getTrainCode();
        // 多个人抢同一个车次，可能发生超卖；多个人抢不同车次，就互不影响了  ->> pass synchronized
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, String.valueOf(Thread.currentThread().getId()), 10, TimeUnit.SECONDS);
        if (lock) {
            LOG.info("恭喜，抢到锁了");
        } else {
            // 只是没抢到锁，并不知道票抢完了没，所以提示稍后再试
            LOG.info("很遗憾，没抢到锁");
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_LOCK_FAIL);
        }
        // 可以购票：todo: 发送MQ，等待出票
    }

    /**
     * 降级方法，需包含限流方法的所有参数和BlockException参数
     */
    public void beforeDoConfirmBlock(ConfirmOrderDoReq req, BlockException e) {
        LOG.info("购票请求被限流：{}", req);
        throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_FLOW_EXCEPTION);
    }

}
