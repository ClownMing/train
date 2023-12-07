package com.ming.train.member.resp;

/**
 * @author clownMing
 */
public class MemberLoginResp {
    private Long id;

    private String mobile;

    public MemberLoginResp(Long id, String mobile) {
        this.id = id;
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
