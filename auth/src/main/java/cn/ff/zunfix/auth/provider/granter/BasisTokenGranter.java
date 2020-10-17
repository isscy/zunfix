package cn.ff.zunfix.auth.provider.granter;

import cn.ff.zunfix.auth.provider.token.BasisAuthenticationToken;
import cn.ff.zunfix.auth.provider.token.SmsAuthenticationToken;
import cn.ff.zunfix.auth.provider.token.UserPasswordAuthenticationToken;
import cn.ff.zunfix.common.core.utils.Json;
import cn.ff.zunfix.common.security.constant.SecurityConstant;
import cn.ff.zunfix.common.security.entity.BaseUserDetail;
import com.alibaba.fastjson.JSON;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Map;

/**
 * 自定义抽象token授予者
 *
 * @author fengfan 2020/8/21
 */
public abstract class BasisTokenGranter extends AbstractTokenGranter {


    private final OAuth2RequestFactory requestFactory;


    protected BasisTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.requestFactory = requestFactory;
    }


    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = tokenRequest.getRequestParameters();
        BaseUserDetail userDetail = getUserFromRequestParam(parameters);
        if (userDetail == null) {
            throw new InvalidGrantException("无法获取用户信息");
        }
        OAuth2Request storedOAuth2Request = this.requestFactory.createOAuth2Request(client, tokenRequest);
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        authentication.setDetails(userDetail);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(storedOAuth2Request, authentication);
        return oAuth2Authentication;
    }

    protected abstract BaseUserDetail getUserFromRequestParam(Map<String, String> parameters);

    protected BasisAuthenticationToken descParamAsToken(Map<String, String> parameters, String grantType) {
        // TODO - 优化 -> use proxy
        BasisAuthenticationToken token;
        if (SecurityConstant.LOGIN_TYPE_SMS.equals(grantType)) {
            token = Json.parseObject(Json.toJsonString(parameters), SmsAuthenticationToken.class);
        } else if (SecurityConstant.LOGIN_TYPE_PASSWORD.equals(grantType)) {
            token = JSON.parseObject(JSON.toJSONString(parameters), UserPasswordAuthenticationToken.class);
        } else {
            token = null;
        }
        return token;
        /*
        Map<String, String> param = new LinkedHashMap<String, String>(parameters);

        String loginType = param.get("loginType");
        String captchaCode = param.get("captchaCode");
        String captchaType = param.get("captchaType");
        String expiration = param.get("expiration");
        String clientId = param.get("clientId");
        String clientSecret = param.get("clientSecret");
        BasisAuthenticationToken token;
        if (SecurityConstant.LOGIN_TYPE_SMS.equals(grantType)) {
            String phone = param.get("phone");
            String code = param.get("code");
            token.set
                    token = new SmsAuthenticationToken(phone);
            token.setLoginType(loginType);
            token.setCaptchaCode(captchaCode);
            token.setCaptchaType(captchaType);
            token.setExpiration(Integer.parseInt(expiration));
            token.setClientId(clientId);
            token.setClientSecret(clientSecret);


        } else if (SecurityConstant.LOGIN_TYPE_PASSWORD.equals(grantType)) {
            String username = param.get("username");
            String password = param.get("password");
            token = new UserPasswordAuthenticationToken();
        } else {
            throw new RuntimeException("不支持的grant");
        }

        return token;
        */
    }
}
