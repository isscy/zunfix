package cn.ff.zunfix.auth.handler;

import cn.ff.zunfix.common.core.constant.ResultEnum;
import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.Rs;
import cn.ff.zunfix.common.core.utils.WebUtil;
import cn.ff.zunfix.common.security.exception.BasisAuthenticationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆失败处理
 */
@Component
@Slf4j
public class LoginAuthenticationFailedHandler implements AuthenticationFailureHandler {

    @Override
    @SneakyThrows
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        R r = Rs.fail(ResultEnum.CLIENT_ERROR);
        if (exception instanceof BasisAuthenticationException) {
            BasisAuthenticationException auth2Exception = (BasisAuthenticationException) exception;
            r.setMessage(auth2Exception.getErrorCode() + auth2Exception.getMessage());
        } else {
            r.setMessage(exception.getMessage());
        }
        WebUtil.writeJson(response, r, HttpStatus.UNAUTHORIZED.value());
    }

}
