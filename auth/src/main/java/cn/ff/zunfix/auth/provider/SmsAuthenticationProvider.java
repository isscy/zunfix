package cn.ff.zunfix.auth.provider;

import cn.ff.zunfix.auth.provider.token.SmsAuthenticationToken;
import cn.ff.zunfix.auth.provider.token.UserPasswordAuthenticationToken;
import cn.ff.zunfix.auth.service.DefaultClientDetailsService;
import cn.ff.zunfix.auth.service.LoginService;
import cn.ff.zunfix.auth.service.UserDetailsServiceImpl;
import cn.ff.zunfix.common.security.entity.SysOauthClientDetails;
import cn.ff.zunfix.common.security.exception.BasisAuthenticationException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * _
 *
 * @author fengfan 2020/8/17
 */
@Component
@AllArgsConstructor
public class SmsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final DefaultClientDetailsService defaultClientDetailsService;
    private final LoginService loginService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        UserDetails user = this.retrieveUser(authenticationToken.getPhone(), authenticationToken);
        this.additionalAuthenticationChecks(user, authentication);
        return this.createSuccessAuthentication(authentication, user);
    }

    @Override
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
        SmsAuthenticationToken authenticationToken = new SmsAuthenticationToken(user, authentication.getAuthorities());
        authenticationToken.setDetailPlus(authentication);
        return authenticationToken;
    }


    /**
     * 获取用户信息
     */
    @Override
    protected UserDetails retrieveUser(String phone, Authentication authentication) throws AuthenticationException {
        if (phone == null || StringUtils.isBlank(phone.trim())) {
            throw new UsernameNotFoundException("手机号不能为空");
        }
        UserDetails user = userDetailsService.loadUserByPhone(phone);
        return user;

    }

    /**
     * 验证信息
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails user, Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        if (user == null || !user.isEnabled()) {
            throw new UsernameNotFoundException("未注册或账号已被锁定");
        }
        /*SysOauthClientDetails clientDetails = defaultClientDetailsService.loadClientByClientId(authenticationToken.getClientId());
        if (clientDetails == null || StringUtils.isBlank(authenticationToken.getClientSecret()) || !passwordEncoder.matches(authenticationToken.getClientSecret(), clientDetails.getClientSecret())) {
            throw new BasisAuthenticationException("client_id或者client_secret错误！");
        }*/
        String code = loginService.getSmsCodeFromRds(authenticationToken.getPhone(), "login");
        if (StringUtils.isBlank(code) || !code.equals(authenticationToken.getCode())) {
            throw new UsernameNotFoundException("验证码错误");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
