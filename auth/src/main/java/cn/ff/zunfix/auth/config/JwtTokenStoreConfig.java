package cn.ff.zunfix.auth.config;

import cn.ff.zunfix.common.core.entity.SysUser;
import cn.ff.zunfix.common.security.entity.BaseUserDetail;
import cn.ff.zunfix.common.security.properties.KeyStoreProperties;
import cn.ff.zunfix.common.security.properties.Oauth2Properties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用Jwt存储token的配置
 *
 * @author fengfan 2020/8/12
 */
@Configuration
@AllArgsConstructor
public class JwtTokenStoreConfig {

    private Oauth2Properties oauth2Properties;





    /*@Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }*/

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的证书中获取秘钥对
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreProperties keyStore = oauth2Properties.getKeyStore();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyStore.getKeyResource(), keyStore.getSecretChars());
        return keyStoreKeyFactory.getKeyPair(keyStore.getAlias(), keyStore.getPasswordChars());
    }


    /****
     * JWT令牌转换器
     */
    /*@Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(CustomUserAuthenticationConverter customUserAuthenticationConverter) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                keyProperties.getKeyStore().getLocation(),                          //证书路径 changgou.jks
                keyProperties.getKeyStore().getSecret().toCharArray())              //证书秘钥 changgouapp
                .getKeyPair(
                        keyProperties.getKeyStore().getAlias(),                     //证书别名 changgou
                        keyProperties.getKeyStore().getPassword().toCharArray());   //证书密码 changgou
        converter.setKeyPair(keyPair);
        //配置自定义的CustomUserAuthenticationConverter
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);
        return converter;
    }*/
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = new HashMap<>(8);
            SysUser sysUser = ((BaseUserDetail) authentication.getPrincipal()).getSysUser();
            additionalInfo.put("id", sysUser.getId());
            additionalInfo.put("userId", sysUser.getId());
            additionalInfo.put("nickName", sysUser.getNickName());
            additionalInfo.put("userName", sysUser.getUserName());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }


    /*@Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        return defaultTokenServices;
    }*/


    /*@Bean
    public ClientDetailsServiceResolver redClientDetailsServiceResolver(SysOauthClientDetailsMapper sysOauthClientDetailsMapper) {
        return () -> new DefaultClientDetailsService(sysOauthClientDetailsMapper);
    }*/
}
