package cn.ff.zunfix.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

/**
 * _
 *
 * @author fengfan 2020/8/9
 */
@SpringCloudApplication
@EnableFeignClients(basePackages = {"cn.ff.zunfix.gateway.service.feign", "cn.ff.zunfix.common.security.service.feign"})
//@MapperScan(basePackages = {"cn.ff.zunfix.common.security.mapper"})
public class GatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }


    /**
     * 用户唯一标识 使用IP 来进行限流
     */
    @Bean(name = "ipKeyResolver")
    public KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostString());
    }
}
