package com.ming.train.common.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.GlobalBouncyCastleProvider;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author clownMing
 */
public class JwtUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 生成一个盐值（秘钥），不能泄漏，且每个项目都应该不一样，可以放到配置文件中
     */
    private static final String key = "clownMing";

    public static String createToken(Map<String, Object> map) {
        LOG.info("开始生成JWT token....");
        DateTime now = DateTime.now();
        DateTime expTime = now.offsetNew(DateField.HOUR, 24);
        // 签发时间
        map.put(JWTPayload.ISSUED_AT, now);
        // 过期时间
        map.put(JWTPayload.EXPIRES_AT, expTime);
        // 生效时间
        map.put(JWTPayload.NOT_BEFORE, now);
        String token = JWTUtil.createToken(map, key.getBytes());
        LOG.info("生成的token: {}", token);
        return token;
    }

    public static boolean validate(String token) {
        LOG.info("开始JWT token校验, token: {}", token);
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        // validate 包含了 verify
        boolean validate = jwt.validate(0);
        LOG.info("JWT token校验结果：{}", validate);
        return validate;
    }

    public static JSONObject getJSONObject(String token) {
        boolean validate = validate(token);
        if(!validate) {
            return null;
        }
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
        JWT jwt = JWTUtil.parseToken(token).setKey(key.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.ISSUED_AT);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.NOT_BEFORE);
        LOG.info("根据token获取原始内容：{}", payloads);
        return payloads;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1L);
        map.put("mobile", "1234");
        boolean validate = validate("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTczMjY4NjM4OTc4NDQ4MTc5MiwibW9iaWxlIjoiMTgwOTM4ODU5OTAiLCJpYXQiOjE3MDE5NTY2NjMsImV4cCI6MTcwMjA0MzA2MywibmJmIjoxNzAxOTU2NjYzfQ.n18viLpc77vsbqe1z8-pmJSMh5oTbMgCiQjO89dmwlw");
    }
}
