package com.ming.train.business.req;

import com.ming.train.common.req.PageReq;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DailyTrainQueryReq extends PageReq {

    /**
     * GET 请求不能使用 JsonFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String code;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DailyTrainQueryReq{" +
                "date=" + date +
                ", code='" + code + '\'' +
                "} " + super.toString();
    }
}
