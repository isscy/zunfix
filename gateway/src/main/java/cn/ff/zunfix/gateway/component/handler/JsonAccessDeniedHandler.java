package cn.ff.zunfix.gateway.component.handler;

import cn.ff.zunfix.common.core.utils.Rs;
import cn.ff.zunfix.gateway.utils.WebfluxResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 403处理
 */
@Slf4j
public class JsonAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        return WebfluxResponseUtil.responseWrite(exchange, HttpStatus.FORBIDDEN.value(), Rs.fail(e.getMessage()));
    }
}
