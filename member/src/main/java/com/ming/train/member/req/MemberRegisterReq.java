package com.ming.train.member.req;

/**
 * @author clownMing
 */
public class MemberRegisterReq {

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
