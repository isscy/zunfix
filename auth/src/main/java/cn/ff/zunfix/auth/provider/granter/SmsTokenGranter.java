package cn.ff.zunfix.auth.provider.granter;

import cn.ff.zunfix.auth.provider.token.SmsAuthenticationToken;
import cn.ff.zunfix.auth.service.UserDetailsServiceImpl;
import cn.ff.zunfix.common.security.constant.SecurityConstant;
import cn.ff.zunfix.common.security.entity.BaseUserDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * 短信登陆grant
 *
 * @author fengfan 2020/8/21
 */
public class SmsTokenGranter extends BasisTokenGranter {

    private UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;


    public SmsTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, UserDetailsServiceImpl userDetailsService) {
        super(tokenServices, clientDetailsService, requestFactory, SecurityConstant.LOGIN_TYPE_SMS);
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = tokenRequest.getRequestParameters();
        SmsAuthenticationToken token = (SmsAuthenticationToken) super.descParamAsToken(parameters, SecurityConstant.LOGIN_TYPE_SMS);
        BaseUserDetail userDetail = (BaseUserDetail) userDetailsService.loadUserByPhone(token.getPhone());
        token.setDetailPlus(parameters, client);
        if (userDetail == null) {
            throw new InvalidGrantException("无法获取用户信息");
        }
        Authentication resultAuth;
        try {
            resultAuth = authenticationManager.authenticate(token);
        } catch (AccountStatusException ase) {
            throw new InvalidGrantException(ase.getMessage());
        }
        if (resultAuth == null || !resultAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + token.getPhone());
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        SmsAuthenticationToken authentication = new SmsAuthenticationToken(userDetail, userDetail.getAuthorities());
        authentication.setDetails(userDetail);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(storedOAuth2Request, authentication);
        return oAuth2Authentication;
    }

    @Override
    protected BaseUserDetail getUserFromRequestParam(Map<String, String> parameters) {
        SmsAuthenticationToken token = (SmsAuthenticationToken) super.descParamAsToken(parameters, SecurityConstant.LOGIN_TYPE_SMS);
        return (BaseUserDetail) userDetailsService.loadUserByPhone(token.getPhone());
    }
}
