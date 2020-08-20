package cn.ff.zunfix.auth.handler;

import cn.ff.zunfix.common.core.constant.ResultEnum;
import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.exception.BasisException;
import cn.ff.zunfix.common.core.utils.Rs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 统一异常处理器
 *
 * @author fengfan 2019-06-05
 */
@Slf4j
@RestControllerAdvice
public class UnifiedExceptionHandler {


    @ExceptionHandler({AuthenticationException.class})
    public static R authenticationException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ex.printStackTrace();
        R resultBody = resolveException(ex, request.getRequestURI());
        response.setStatus(401);
        return resultBody;
    }

    /**
     * 参数绑定
     */
    @ExceptionHandler(BindException.class)
    public static R bindException(Exception e) {
        e.printStackTrace();
        BindException bindException = (BindException) e;
        List<ObjectError> allErrors = bindException.getBindingResult().getAllErrors();
        String msg = allErrors.get(0).getDefaultMessage();
        return Rs.fail(ResultEnum.CLIENT_PARAM_405.getCode(), msg);
    }

    /**
     * 参数验证
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public static R methodArgumentNotValidException(Exception e) {
        MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
        List<ObjectError> allErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        String msg = allErrors.get(0).getDefaultMessage();
        return Rs.fail(ResultEnum.CLIENT_PARAM_405.getCode(), msg);
    }

    /**
     * 断言
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public static R illegalArgumentException(Exception e) {
        String msg = e.getMessage();
        return Rs.fail(msg);
    }


    /**
     * OAuth2Exception
     */
    @ExceptionHandler({OAuth2Exception.class, InvalidTokenException.class})
    public static R oauth2Exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ex.printStackTrace();
        R r = resolveException(ex, request.getRequestURI());
        response.setStatus(401);
        return r;
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler({BasisException.class})
    public static R openException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        R r = resolveException(ex, request.getRequestURI());
        response.setStatus(500); // TODO 修改状态码
        return r;
    }

    /**
     * 其他异常
     */
    @ExceptionHandler({Exception.class})
    public static R exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ex.printStackTrace();
        R r = resolveException(ex, request.getRequestURI());
        response.setStatus(500);
        return r;
    }


    /**
     * 静态解析异常  ==>  新的 现在用这个
     */
    public static R resolveException(Exception ex, String path) {
        ResultEnum code = ResultEnum.ERROR;
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String message = ex.getMessage();
        String superClassName = ex.getClass().getSuperclass().getName();
        String className = ex.getClass().getName();
        if (className.contains("AccessDeniedException")) { // 资源服务器 拒绝
            code = ResultEnum.ACCESS_DENIED;
            httpStatus = HttpStatus.FORBIDDEN.value();
        } else if (superClassName.contains("AuthenticationException")) { // 认证服务器 拒绝
            code = ResultEnum.CLIENT_ERROR_401;
            httpStatus = HttpStatus.UNAUTHORIZED.value();
        }
        return buildBody(ex, code, path, httpStatus);
    }

    /**
     * 静态解析认证异常
     */
    public static R resolveOauthException(Exception ex, String path) {
        ResultEnum code = ResultEnum.BAD_CREDENTIALS;
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        if (ex instanceof AuthenticationException) {
            httpStatus = HttpStatus.UNAUTHORIZED.value(); // 认证错误！
            if (ex instanceof UsernameNotFoundException) {
                code = ResultEnum.USERNAME_NOT_FOUND;
            }
        } else if (ex instanceof OAuth2Exception) {
            httpStatus = HttpStatus.UNAUTHORIZED.value(); // 认证错误！
            if (ex instanceof InvalidGrantException) {
                code = ResultEnum.INVALID_GRANT;
            }
        }
        return buildBody(ex, code, path, httpStatus);
    }


    /**
     * 静态解析异常

     public static R resolveException(Exception ex, String path) {
     ResultEnum code = ResultEnum.ERROR;
     int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
     String message = ex.getMessage();
     String superClassName = ex.getClass().getSuperclass().getName();
     String className = ex.getClass().getName();
     if (className.contains("UsernameNotFoundException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.USERNAME_NOT_FOUND;
     } else if (className.contains("BadCredentialsException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.BAD_CREDENTIALS;
     } else if (className.contains("OAuth2AuthenticationException") || className.contains("AuthenticationCredentialsNotFoundException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.CLIENT_ERROR_401;
     } else if (className.contains("AccountExpiredException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.ACCOUNT_EXPIRED;
     } else if (className.contains("LockedException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.ACCOUNT_LOCKED;
     } else if (className.contains("DisabledException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.ACCOUNT_DISABLED;
     } else if (className.contains("CredentialsExpiredException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.CREDENTIALS_EXPIRED;
     } else if (className.contains("InvalidClientException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.INVALID_CLIENT;
     } else if (className.contains("UnauthorizedClientException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.UNAUTHORIZED_CLIENT;
     } else if (className.contains("InsufficientAuthenticationException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.UNAUTHORIZED;
     } else if (className.contains("InvalidGrantException")) {
     code = ResultEnum.INVALID_GRANT;
     if ("Bad credentials".contains(message)) {
     code = ResultEnum.BAD_CREDENTIALS;
     } else if ("User is disabled".contains(message)) {
     code = ResultEnum.ACCOUNT_DISABLED;
     } else if ("User account is locked".contains(message)) {
     code = ResultEnum.ACCOUNT_LOCKED;
     }
     } else if (className.contains("InvalidScopeException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.INVALID_SCOPE;
     } else if (className.contains("InvalidTokenException")) {
     httpStatus = HttpStatus.UNAUTHORIZED.value();
     code = ResultEnum.INVALID_TOKEN;
     } else if (className.contains("InvalidRequestException")) {
     httpStatus = HttpStatus.BAD_REQUEST.value();
     code = ResultEnum.INVALID_REQUEST;
     } else if (className.contains("RedirectMismatchException")) {
     code = ResultEnum.REDIRECT_URI_MISMATCH;
     } else if (className.contains("UnsupportedGrantTypeException")) {
     code = ResultEnum.UNSUPPORTED_GRANT_TYPE;
     } else if (className.contains("UnsupportedResponseTypeException")) {
     code = ResultEnum.UNSUPPORTED_RESPONSE_TYPE;
     } else if (className.contains("UserDeniedAuthorizationException")) {
     code = ResultEnum.ACCESS_DENIED;
     } else if (className.contains("AccessDeniedException")) {
     code = ResultEnum.ACCESS_DENIED;
     httpStatus = HttpStatus.FORBIDDEN.value();
     if (ResultEnum.ACCESS_DENIED_BLACK_IP_LIMITED.getMessage().contains(message)) {
     code = ResultEnum.ACCESS_DENIED_BLACK_IP_LIMITED;
     } else if (ResultEnum.ACCESS_DENIED_AUTHORITY_EXPIRED.getMessage().contains(message)) {
     code = ResultEnum.ACCESS_DENIED_AUTHORITY_EXPIRED;
     }
     return buildBody(code, path, httpStatus);
     } else if (className.contains("HttpMessageNotReadableException")
     || className.contains("TypeMismatchException")
     || className.contains("MissingServletRequestParameterException")) {
     httpStatus = HttpStatus.BAD_REQUEST.value();
     code = ResultEnum.BAD_REQUEST;
     } else if (className.contains("NoHandlerFoundException")) {
     httpStatus = HttpStatus.NOT_FOUND.value();
     code = ResultEnum.NOT_FOUND;
     } else if (className.contains("HttpRequestMethodNotSupportedException")) {
     httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();
     code = ResultEnum.METHOD_NOT_ALLOWED;
     } else if (className.contains("HttpMediaTypeNotAcceptableException")) {
     httpStatus = HttpStatus.BAD_REQUEST.value();
     code = ResultEnum.MEDIA_TYPE_NOT_ACCEPTABLE;
     } else if (className.contains("MethodArgumentNotValidException")) {
     BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
     code = ResultEnum.CLIENT_PARAM_405;
     return Rs.fail(code.getCode(), bindingResult.getFieldError().getDefaultMessage());
     } else if (className.contains("IllegalArgumentException")) {
     //参数错误
     code = ResultEnum.CLIENT_PARAM_405;
     httpStatus = HttpStatus.BAD_REQUEST.value();
     } else if (className.contains("OpenAlertException")) {
     code = ResultEnum.ALARM;
     } else if (className.contains("OpenSignatureException")) {
     code = ResultEnum.SIGNATURE_DENIED;
     }
     return buildBody(ex, code, path, httpStatus);
     }
     */
    /**
     * 构建返回结果对象
     */
    private static R buildBody(Exception exception, ResultEnum resultCode, String path, int httpStatus) {
        resultCode = resultCode == null ? ResultEnum.ERROR : resultCode;
        R r = Rs.fail(resultCode.getCode(), exception.getMessage())/*.httpStatus(httpStatus).path(path)*/;
        if (httpStatus >= 500) {
            log.error("====> System Error:{} exception: {}", r, exception);
        } else {
            log.warn("====> Client Error: {}, path: {}", r.getMessage(), path);
        }

        return r;
    }

    private static R buildBody(ResultEnum resultCode, String path, int httpStatus) {
        resultCode = resultCode == null ? ResultEnum.ERROR : resultCode;
        log.error("==> error : ResultEnum.code: {} ResultEnum.msg: {}", resultCode.getCode(), resultCode.getMessage());
        return Rs.fail(resultCode.getCode(), resultCode.getMessage())/*.httpStatus(httpStatus).path(path)*/;
    }


}
