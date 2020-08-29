package cn.ff.zunfix.auth.provider.granter;

import cn.ff.zunfix.auth.service.UserDetailsServiceImpl;
import cn.ff.zunfix.common.security.constant.SecurityConstant;
import cn.ff.zunfix.common.security.entity.BaseUserDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Map;

/**
 * 刷新token 的 granter
 *
 * @author fengfan 2020/8/25
 */
public class RefreshNewTokenGranter extends BasisTokenGranter {


    private UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;


    public RefreshNewTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, UserDetailsServiceImpl userDetailsService, TokenStore tokenStore) {
        super(tokenServices, clientDetailsService, requestFactory, SecurityConstant.LOGIN_TYPE_REFRESH);
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected BaseUserDetail getUserFromRequestParam(Map<String, String> parameters) {
        return null;
    }
}
