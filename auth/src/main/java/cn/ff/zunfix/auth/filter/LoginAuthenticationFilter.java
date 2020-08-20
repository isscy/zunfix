package cn.ff.zunfix.auth.filter;

import cn.ff.zunfix.auth.provider.token.UserPasswordAuthenticationToken;
import cn.ff.zunfix.common.core.utils.Json;
import cn.ff.zunfix.common.security.constant.SecurityConstant;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 用户多端登录拦截器
 * 通过grant_type区分登录端
 *
 * @author fengfan at 2020-08-19
 */
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication请求方法不支持: " + request.getMethod());
        }
        String grantType = obtainParameter(request, OAuth2Utils.GRANT_TYPE);
        String loginType = obtainParameter(request, SecurityConstant.PARAMETER_LOGIN_TYPE);
        AbstractAuthenticationToken authRequest = null;
        String requestBody = getStringFromStream(request); // TODO :转成JSON解析
        if (StringUtils.isEmpty(requestBody)) {
            throw new AuthenticationServiceException("请求参数为空");
        }
        /*if (SecurityConstant.REQUEST_GRANT_TYPE_WXM.equals(type)) { // 通过微信小程序登录 => 通过code
            authRequest = Json.parseObject(requestBody, MiniAppAuthenticationToken.class);
        } else if (SecurityConstant.REQUEST_GRANT_TYPE_USER.equals(type)) { //  通过用户名/密码登录
            authRequest = Json.parseObject(requestBody, UserAuthenticationToken.class);
        } else if (SecurityConstant.REQUEST_GRANT_TYPE_PHONE.equals(type)) { // 通过手机号/验证码登录
            authRequest = Json.parseObject(requestBody, PhoneAuthenticationToken.class);
        } else if (SecurityConstant.REQUEST_GRANT_TYPE_QR.equals(type)) { // 通过APP扫码登录
            authRequest = Json.parseObject(requestBody, QrAuthenticationToken.class);
        }*/
        authRequest = Json.parseObject(requestBody, UserPasswordAuthenticationToken.class);
        if (authRequest == null) {
            throw new AuthenticationServiceException("序列化token错误");
        }
        setDetails(request, authRequest);// Allow subclasses to set the "details" property
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainParameter(HttpServletRequest request, String parameter) {
        String result = request.getParameter(parameter);
        return result == null ? "" : result;
    }


    private String getStringFromStream(HttpServletRequest req) {
        ServletInputStream is;
        try {
            is = req.getInputStream();
            int nRead = 1;
            int nTotalRead = 0;
            byte[] bytes = new byte[10240];
            while (nRead > 0) {
                nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
                if (nRead > 0) {
                    nTotalRead = nTotalRead + nRead;
                }
            }
            return new String(bytes, 0, nTotalRead, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
