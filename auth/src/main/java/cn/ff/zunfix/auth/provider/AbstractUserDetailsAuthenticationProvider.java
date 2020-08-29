package cn.ff.zunfix.auth.provider;

import cn.ff.zunfix.auth.provider.token.BasisAuthenticationToken;
import cn.ff.zunfix.auth.service.DefaultClientDetailsService;
import cn.ff.zunfix.common.security.entity.SysOauthClientDetails;
import cn.ff.zunfix.common.security.exception.BasisAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义 基础Provider
 *
 * @author fengfan 2020/8/18
 */
@Slf4j
public abstract class AbstractUserDetailsAuthenticationProvider implements AuthenticationProvider, InitializingBean {


    protected abstract void additionalAuthenticationChecks(UserDetails var1, Authentication var2) throws AuthenticationException;

    @Override
    public final void afterPropertiesSet() {
    }


    @Override
    public abstract Authentication authenticate(Authentication authentication) throws AuthenticationException;

    protected abstract Authentication createSuccessAuthentication(Authentication authentication, UserDetails user);


    protected abstract UserDetails retrieveUser(String username, Authentication authentication) throws AuthenticationException;



}
