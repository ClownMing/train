package com.ming.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.ming.train.common.exception.BusinessException;
import com.ming.train.common.exception.BusinessExceptionEnum;
import com.ming.train.common.util.SnowUtil;
import com.ming.train.member.domain.Member;
import com.ming.train.member.domain.MemberExample;
import com.ming.train.member.mapper.MemberMapper;
import com.ming.train.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author clownMing
 */
@Service
public class MemberService {

    @Resource
    private MemberMapper memberMapper;

    public int count() {
        return (int) memberMapper.countByExample(null);
    }

    public long register(MemberRegisterReq registerReq) {
        String mobile = registerReq.getMobile();
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        if(CollUtil.isNotEmpty(list)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        // 生成雪花算法 id 参数1：终端ID  参数2：数据中心ID
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }
}
