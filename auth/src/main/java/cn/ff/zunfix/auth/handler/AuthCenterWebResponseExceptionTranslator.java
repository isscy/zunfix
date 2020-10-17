package cn.ff.zunfix.auth.handler;

import cn.ff.zunfix.common.core.constant.ResultEnum;
import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.Rs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.server.ServerErrorException;

import java.io.IOException;

/**
 * 认证微服务 的错误处理
 */
@Component("authCenterWebResponseExceptionTranslator")
public class AuthCenterWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthCenterWebResponseExceptionTranslator.class);
    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<Object> translate(Exception e) throws Exception {
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        LOGGER.error(e.getMessage());
        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(ResultEnum.CLIENT_AUTH_401); // 401
        }
        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(ResultEnum.CLIENT_AUTH_401); // 401
        }
        ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(ResultEnum.CLIENT_ACCESS_403); // 403
        }
        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase != null) {
            return handleOAuth2Exception(ResultEnum.CLIENT_METHOD_405); // 405
        }
        return handleOAuth2Exception(ResultEnum.SERVER_ERROR); // 500
    }


    private ResponseEntity<Object> handleOAuth2Exception(ResultEnum re) {
        int status = re.getHttpCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        //ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(e, headers, HttpStatus.valueOf(status));
        return ResponseEntity.status(re.getHttpCode()).headers(headers).body(Rs.fail(re));
    }


    /*@Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(oAuth2Exception.getHttpErrorCode())
                .body(new DefaultOauth2Exception(oAuth2Exception.getMessage()));
    }*/


    /*@Override
    public ResponseEntity translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        R result = UnifiedExceptionHandler.resolveOauthException(e,request.getRequestURI());
        return ResponseEntity.status(200).body(result);
    }*/


}
