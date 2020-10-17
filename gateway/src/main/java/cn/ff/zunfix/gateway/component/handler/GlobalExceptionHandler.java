package cn.ff.zunfix.gateway.component.handler;

import cn.ff.zunfix.common.core.constant.ResultEnum;
import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.Rs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局异常处理
 *
 * @author fengfan 2020/10/17
 */
@Slf4j
@Order(-1)
@Configuration
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {


    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        final Rx r = getResultResponseDate(ex);
        response.setStatusCode(r.getHttpCode());
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                return bufferFactory.wrap(objectMapper.writeValueAsBytes(r));
            } catch (JsonProcessingException e) {
                log.error("Error writing response", ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }


    public Rx getResultResponseDate(Throwable ex) {
        R r = Rs.fail(ex.getMessage());
        HttpStatus httpCode = HttpStatus.OK;
        if (ex instanceof ResponseStatusException) {
            if (ex instanceof org.springframework.cloud.gateway.support.NotFoundException) {
                httpCode = HttpStatus.SERVICE_UNAVAILABLE;
                r = Rs.fail(ResultEnum.SERVICE_ERROR_UNAVAILABLE);
            }
        }
        return new Rx(r, httpCode);
    }

    private static class Rx extends R {
        private HttpStatus httpCode;

        public Rx(R r) {
            this.setCode(r.getCode());
            this.setMessage(r.getMessage());
            this.setData(r.getData());
            this.setHttpCode(HttpStatus.OK);
        }

        public Rx(R r, HttpStatus httpCode) {
            this(r);
            this.setHttpCode(httpCode);
        }

        public void setHttpCode(HttpStatus httpCode) {
            this.httpCode = httpCode;
        }

        public HttpStatus getHttpCode() {
            return httpCode;
        }


    }
}
