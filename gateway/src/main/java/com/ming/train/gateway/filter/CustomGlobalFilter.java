package com.ming.train.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author clownMing
 */
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(CustomGlobalFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOG.info("CustomGlobalFilter have done~~");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 从小到达执行，数字越小越先执行
        return 0;
    }
}
