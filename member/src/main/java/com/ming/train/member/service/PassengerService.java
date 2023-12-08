package com.ming.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.ming.train.common.context.LoginMemberContext;
import com.ming.train.common.util.SnowUtil;
import com.ming.train.member.domain.Passenger;
import com.ming.train.member.domain.PassengerExample;
import com.ming.train.member.mapper.PassengerMapper;
import com.ming.train.member.req.PassengerQueryReq;
import com.ming.train.member.req.PassengerSaveReq;
import com.ming.train.member.resp.PassengerQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<PassengerQueryResp> queryList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if(ObjectUtil.isNotNull(req.getMemberId())) {
            criteria.andMemberIdEqualTo(req.getMemberId());
        }
        List<Passenger> passengerList = passengerMapper.selectByExample(passengerExample);
        List<PassengerQueryResp> respList = BeanUtil.copyToList(passengerList, PassengerQueryResp.class);
        return respList;
    }
}
