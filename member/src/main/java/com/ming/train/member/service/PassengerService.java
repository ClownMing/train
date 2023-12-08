package com.ming.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.ming.train.common.context.LoginMemberContext;
import com.ming.train.common.util.SnowUtil;
import com.ming.train.member.domain.Passenger;
import com.ming.train.member.mapper.PassengerMapper;
import com.ming.train.member.req.PassengerSaveReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author clownMing
 */
@Service
public class PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    public void save(PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setMemberId(LoginMemberContext.getId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }
}
