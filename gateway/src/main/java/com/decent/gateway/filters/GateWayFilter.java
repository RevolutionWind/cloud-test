package com.decent.gateway.filters;

import com.alibaba.fastjson.JSON;
import com.decent.gateway.pojo.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 全局网关过滤器
 *
 * @author sunxy
 */
@Slf4j
@Component
public class GateWayFilter implements GlobalFilter, Ordered {
    private final static String USER_AUTH_URL = "/user/auth/auto/login";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 登录则不校验
        if (StringUtils.equalsIgnoreCase(request.getURI().getPath(), USER_AUTH_URL)) {
            String userAuthCode = request.getQueryParams().getFirst("userCode");
            if (StringUtils.isBlank(userAuthCode)) {
                return filterResponse(HttpStatus.BAD_REQUEST.value(), "请传输授权码", exchange.getResponse());
            }
            log.info("[{}]授权登录", userAuthCode);
            return chain.filter(exchange);
        }
        List<String> userCodeParams = request.getHeaders().get("userCode");
        if (CollectionUtils.isEmpty(userCodeParams)) {
            return filterResponse(HttpStatus.BAD_REQUEST.value(), "请传输授权码", exchange.getResponse());
        }
        String userCodeKey = userCodeParams.get(0);
        String userCode = String.valueOf(stringRedisTemplate.opsForValue().get(userCodeKey));
        if (StringUtils.isNotBlank(userCode)) {
            log.info("[{}]授权访问", userCode);
            return chain.filter(exchange);
        }
        log.warn("[{}]未授权访问", userCode);
        return filterResponse(HttpStatus.UNAUTHORIZED.value(), "未通过授权，请重新登录", exchange.getResponse());
    }

    private Mono<Void> filterResponse(int code, String msg, ServerHttpResponse response) {
        Response<?> data = new Response<>();
        data.setCode(code);
        data.setMessage(msg);
        byte[] datas = JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(datas);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -2;
    }
}