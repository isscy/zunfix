package cn.ff.zunfix.auth.handler;

import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.WebUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未认证处理
 *
 * @author fengfan 2019-06-25
 */
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        R result = UnifiedExceptionHandler.resolveException(exception, request.getRequestURI());
        WebUtil.writeJson(response, result, 401); // TODO 处理状态码
    }


}
