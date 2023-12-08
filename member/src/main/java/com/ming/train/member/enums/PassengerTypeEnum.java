package com.ming.train.member.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

/**
 * @author clownMing
 */
public enum PassengerTypeEnum {
    // 开发人员用  数据库用   前端展示用

    ADULT("1", "成人"),
    CHILD("2", "儿童"),
    STUDENT("3", "学生");

    private String code;

    private String desc;

    PassengerTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static List<HashMap<String, String>> getEnumList() {

        List<HashMap<String, String>> list = new ArrayList<>();
        for (PassengerTypeEnum item : EnumSet.allOf(PassengerTypeEnum.class)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("code", item.code);
            map.put("desc", item.desc);
            list.add(map);
        }
        return list;
    }

}
