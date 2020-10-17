package cn.ff.zunfix.auth.handler.aspect;

import cn.ff.zunfix.auth.entity.TokenResult;
import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.Rs;
import com.alibaba.druid.wall.violation.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * _
 *
 * @author fengfan 2020/10/14
 */
@Component
@Aspect
public class TokenResponseAspect {


    private final static Logger logger = LoggerFactory.getLogger(TokenResponseAspect.class);

    /// @Around 改变controller返回值的
    @Around("execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        // 放行
        TokenResult response = new TokenResult(Rs.fail("unKonwError"));
        Object proceed = pjp.proceed();
        if (proceed != null) {
            ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
            OAuth2AccessToken body = responseEntity.getBody();
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                response = new TokenResult(Rs.ok(body));
            } else {
                logger.error("error:{}", responseEntity.getStatusCode().toString());
                response = new TokenResult(Rs.fail("shib "));
            }
        }
        return ResponseEntity.status(200).body(response);

    }

}
