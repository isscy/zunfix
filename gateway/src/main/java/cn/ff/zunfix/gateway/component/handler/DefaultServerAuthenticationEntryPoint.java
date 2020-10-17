package cn.ff.zunfix.gateway.component.handler;

import cn.ff.zunfix.common.core.utils.Rs;
import cn.ff.zunfix.gateway.utils.WebfluxResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 401处理
 *
 * @author fengfan 2020/10/15
 */
@Slf4j
public class DefaultServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        return WebfluxResponseUtil.responseWrite(exchange, HttpStatus.UNAUTHORIZED.value(), Rs.fail(e.getMessage()));
    }
}
