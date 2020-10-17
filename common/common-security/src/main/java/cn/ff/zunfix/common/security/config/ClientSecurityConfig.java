package cn.ff.zunfix.common.security.config;


import cn.ff.zunfix.common.security.component.store.UnifyTokenStore;
import cn.ff.zunfix.common.security.properties.Oauth2Properties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * _
 *
 * @author fengfan 2020/9/29
 */
@Configuration
public class ClientSecurityConfig {


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "zunfix.security.token-store", name = "type", havingValue = "redis", matchIfMissing = false)
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory, Oauth2Properties oauth2Properties) {
        return new UnifyTokenStore(connectionFactory, oauth2Properties);
    }



    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



}
