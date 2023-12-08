package com.ming.train.common.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ming.train.common.context.LoginMemberContext;
import com.ming.train.common.resp.MemberLoginResp;
import com.ming.train.common.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author clownMing
 * 拦截器：Spring 框架特有的，常用于登录校验，权限校验
 */
@Component
public class MemberInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MemberInterceptor.class);

    /**
     * preHandle晚于filter，早于dispatcherServlet
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取header的token参数
        String token = request.getHeader("token");
        if(StrUtil.isNotBlank(token)) {
            LOG.info("gateway loginMemberFilter验证该token > {} 通过", token.substring(0, 20));
            LOG.info(">>>>> common MemberInterceptor拦截开始 >>>>>");
            LOG.info("获取会员登录token：{}", token);
            JSONObject memberJsonObject = JwtUtils.getJSONObject(token);
            LOG.info("当前登录会员：{}", memberJsonObject);
            LoginMemberContext.setMember(JSONUtil.toBean(memberJsonObject, MemberLoginResp.class));
        }
        LOG.info(">>>>> common MemberInterceptor拦截结束 >>>>>");
        return true;
    }
}
