package cn.ff.zunfix.auth.config;

import cn.ff.zunfix.auth.mapper.SysOauthClientDetailsMapper;
import cn.ff.zunfix.auth.service.DefaultClientDetailsService;
import cn.ff.zunfix.auth.token.JwtTokenEnhancer;
import cn.ff.zunfix.common.security.service.ClientDetailsServiceResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 使用Jwt存储token的配置
 *
 * @author fengfan 2020/8/12
 */
@Configuration
public class JwtTokenStoreConfig {


    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("test_key");//配置JWT使用的秘钥
        return accessTokenConverter;
    }

    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }

    //@Bean
    public ClientDetailsServiceResolver redClientDetailsServiceResolver(SysOauthClientDetailsMapper sysOauthClientDetailsMapper) {
        return () -> new DefaultClientDetailsService(sysOauthClientDetailsMapper);
    }
}
