package com.ming.train.gateway.filter;

import com.ming.train.gateway.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author clownMing
 */
@Component
public class LoginMemberFilter implements GlobalFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        //排除不需要拦截的请求
        if(path.contains("/admin")
        || path.contains("/member/member/login")
        || path.contains("/hello")
        || path.contains("/member/member/send-code")) {
            LOG.info("该请求：{} > 不需要登录验证", path);
            return chain.filter(exchange);
        }
        LOG.info("路径：{} > 需要进行验证", path);
        // 获取header的 token参数
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String token = headers.getFirst("token");
        LOG.info("会员登录验证开始，token：{}", token);
        if(token == null || token.isEmpty()) {
            LOG.info("token为空，请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 校验token是否有效，包括token是否被改过，是否过期等
        boolean validate = JwtUtils.validate(token);
        if(!validate) {
            // 进行拦截
            LOG.info("token无效，请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        LOG.info("token有效，放行该请求");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 从小到达执行，数字越小越先执行
        return 0;
    }
}
