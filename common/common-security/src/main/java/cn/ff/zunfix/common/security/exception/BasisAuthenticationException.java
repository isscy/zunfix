package cn.ff.zunfix.common.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

/**
 * _
 *
 * @author fengfan 2020/3/17
 */
public class BasisAuthenticationException extends AuthenticationException {

    @Getter
    private Integer errorCode;


    public BasisAuthenticationException(String msg) {
        super(msg);
    }

    public int httpCode() { // 返回 400 无效的请求
        return HttpStatus.BAD_REQUEST.value();
    }


}
