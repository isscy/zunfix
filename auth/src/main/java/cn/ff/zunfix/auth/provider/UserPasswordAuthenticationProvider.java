package cn.ff.zunfix.auth.provider;

import cn.ff.zunfix.auth.provider.token.UserPasswordAuthenticationToken;
import cn.ff.zunfix.auth.service.DefaultClientDetailsService;
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
public class UserPasswordAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final DefaultClientDetailsService defaultClientDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserPasswordAuthenticationToken authenticationToken = (UserPasswordAuthenticationToken) authentication;
        UserDetails user = this.retrieveUser(authenticationToken.getUsername(), authenticationToken);
        this.additionalAuthenticationChecks(user, authentication);
        return this.createSuccessAuthentication(authentication, user);
    }

    @Override
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {
        UserPasswordAuthenticationToken authenticationToken = new UserPasswordAuthenticationToken(user, authentication.getAuthorities());
        authenticationToken.setDetailPlus(authentication);
        return authenticationToken;
    }


    @Override
    protected UserDetails retrieveUser(String username, Authentication authentication) throws AuthenticationException {
        if (username == null || StringUtils.isBlank(username.trim())) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        UserDetails user = userDetailsService.loadUserByUsername(username);
        return user;

    }


    @Override
    protected void additionalAuthenticationChecks(UserDetails user, Authentication authentication) throws AuthenticationException {
        if (user == null || !user.isEnabled()) {
            throw new UsernameNotFoundException("未注册或账号已被锁定");
        }
        String encodedPassword = user.getPassword();
        String rawPassword = authentication.getCredentials().toString();
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UsernameNotFoundException("账号或密码不正确");
        }
        UserPasswordAuthenticationToken token = (UserPasswordAuthenticationToken) authentication;
        SysOauthClientDetails clientDetails = defaultClientDetailsService.loadClientByClientId(token.getClientId());
        if (clientDetails == null || StringUtils.isBlank(token.getClientSecret()) || !passwordEncoder.matches(token.getClientSecret(), clientDetails.getClientSecret())) {
            throw new BasisAuthenticationException("client_id或者client_secret错误！");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
