package com.ming.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ming.train.common.exception.BusinessException;
import com.ming.train.common.exception.BusinessExceptionEnum;
import com.ming.train.common.util.JwtUtils;
import com.ming.train.common.util.SnowUtil;
import com.ming.train.member.domain.Member;
import com.ming.train.member.domain.MemberExample;
import com.ming.train.member.mapper.MemberMapper;
import com.ming.train.member.req.MemberLoginReq;
import com.ming.train.member.req.MemberRegisterReq;
import com.ming.train.member.req.MemberSendCodeReq;
import com.ming.train.member.resp.MemberLoginResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author clownMing
 */
@Service
public class MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);

    @Resource
    private MemberMapper memberMapper;

    public int count() {
        return (int) memberMapper.countByExample(null);
    }

    public long register(MemberRegisterReq registerReq) {
        String mobile = registerReq.getMobile();
        Member memberDB = selectByMobile(mobile);
        if(ObjectUtil.isNotNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member newMember = new Member();
        // 生成雪花算法 id 参数1：终端ID  参数2：数据中心ID
        newMember.setId(SnowUtil.getSnowflakeNextId());
        newMember.setMobile(mobile);
        memberMapper.insert(newMember);
        return newMember.getId();
    }

    public void sendCode(MemberSendCodeReq sendCodeReq) {
        // 判断是否已经注册过
        String mobile = sendCodeReq.getMobile();
        Member memberDB = selectByMobile(mobile);
        // 如果手机号不存在，则插入一条记录
        if(ObjectUtil.isNull(memberDB)) {
            LOG.info("手机号不存在，插入一条记录");
            Member member = new Member();
            // 生成雪花算法 id 参数1：终端ID  参数2：数据中心ID
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        }
        // 生成验证码
//        String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("手机号存在 > 生成短信验证码：{}", code);
        // 保存短信记录表：手机号，短信验证码，有效期，是否已使用，业务类型，发送时间，使用时间
        LOG.info("保存短信记录表");
        // 对接短信通道，发送短信
        LOG.info("对接短信通道");
    }

    public MemberLoginResp login(MemberLoginReq memberLoginReq) {
        String mobile = memberLoginReq.getMobile();
        String code = memberLoginReq.getCode();
        // 根据 mobile 去数据库中查询
        Member memberDB = selectByMobile(mobile);
        // 如果手机号不存在，中断登录流程
        if(ObjectUtil.isNull(memberDB)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }
        // 再校验短信验证码
        if(!"8888".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }
        MemberLoginResp memberLoginResp =
                BeanUtil.copyProperties(memberDB, MemberLoginResp.class);
        // 生成jwt token
        String token = JwtUtils.createToken(BeanUtil.beanToMap(memberLoginResp));
        memberLoginResp.setToken(token);
        return memberLoginResp;
    }

    private Member selectByMobile(String mobile) {
        MemberExample memberExample = new MemberExample();
        memberExample.createCriteria().andMobileEqualTo(mobile);
        List<Member> list = memberMapper.selectByExample(memberExample);
        // 如果手机号不存在，则插入一条记录
        if(CollUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
