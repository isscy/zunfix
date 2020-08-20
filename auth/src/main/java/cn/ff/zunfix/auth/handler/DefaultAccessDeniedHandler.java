package cn.ff.zunfix.auth.handler;

import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.WebUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问被拒绝
 *
 * @author fengfan 2018-12-10
 */
@Component("defaultAccessDeniedHandler")
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
    /*@Autowired
    private ObjectMapper objectMapper;*/

    /*@Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("error", "400");
        map.put("message", accessDeniedException.getMessage());
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf(new Date().getTime()));
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }*/


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        R result = UnifiedExceptionHandler.resolveException(exception, request.getRequestURI());
        response.setStatus(401);
        WebUtil.writeJson(response, result);
    }


}
