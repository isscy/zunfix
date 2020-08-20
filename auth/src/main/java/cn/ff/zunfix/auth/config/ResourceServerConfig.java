package cn.ff.zunfix.auth.config;

import cn.ff.zunfix.auth.filter.LoginAuthenticationFilter;
import cn.ff.zunfix.auth.handler.DefaultAccessDeniedHandler;
import cn.ff.zunfix.auth.handler.DefaultAuthenticationEntryPoint;
import cn.ff.zunfix.auth.handler.LoginAuthenticationFailedHandler;
import cn.ff.zunfix.auth.handler.LoginAuthenticationSuccessHandler;
import cn.ff.zunfix.common.security.properties.Oauth2Properties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 资源服务器配置
 *
 * @author fengfan 2020/8/12
 */
@AllArgsConstructor
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private final Oauth2Properties oauth2Properties;
    private final AuthenticationManager authenticationManager;
    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    private final LoginAuthenticationFailedHandler loginAuthenticationFailedHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers(oauth2Properties.unCheckUrlArray()).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .exceptionHandling().accessDeniedHandler(defaultAccessDeniedHandler)
                .authenticationEntryPoint(new DefaultAuthenticationEntryPoint());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 定义异常转换类生效
        /*AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setExceptionTranslator(new DefaultWebResponseExceptionTranslator());*/
        OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
        authenticationEntryPoint.setExceptionTranslator(new DefaultWebResponseExceptionTranslator());
        resources.authenticationEntryPoint(authenticationEntryPoint);
    }


    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(loginAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(loginAuthenticationFailedHandler);
        return filter;
    }




}
