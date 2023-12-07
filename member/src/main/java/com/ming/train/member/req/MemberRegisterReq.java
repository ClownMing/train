package com.ming.train.member.req;

import jakarta.validation.constraints.NotBlank;

/**
 * @author clownMing
 * 会员-注册请求
 */
public class MemberRegisterReq {

    @NotBlank(message = "【手机号】不能为空")
    String mobile;

    public MemberRegisterReq() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "MemberRegisterReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }
}
