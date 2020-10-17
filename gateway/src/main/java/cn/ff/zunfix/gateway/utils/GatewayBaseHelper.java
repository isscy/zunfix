package cn.ff.zunfix.gateway.utils;

//import cn.ff.zunfix.common.security.constant.SecurityConstant;
import cn.ff.zunfix.common.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * _
 *
 * @author fengfan 2020/9/9
 */
@Slf4j
public class GatewayBaseHelper {


    /**
     * 从请求中获取token
     */
    public static String getTokenFromReq(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(SecurityConstant.JWT_TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return token.replace(SecurityConstant.JWT_TOKEN_PREFIX , "");
    }

    /**
     * 获取客户端IP地址
     */
    public static String getRemoteAddr(ServerHttpRequest request) {
        Map<String, String> headers = request.getHeaders().toSingleValueMap();
        String ip = headers.get("X-Forwarded-For");
        if (isEmptyIP(ip)) {
            ip = headers.get("Proxy-Client-IP");
            if (isEmptyIP(ip)) {
                ip = headers.get("WL-Proxy-Client-IP");
                if (isEmptyIP(ip)) {
                    ip = headers.get("HTTP_CLIENT_IP");
                    if (isEmptyIP(ip)) {
                        ip = headers.get("HTTP_X_FORWARDED_FOR");
                        if (isEmptyIP(ip)) {
                            ip = request.getRemoteAddress().getAddress().getHostAddress();
                            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                                ip = getLocalAddr();// 根据网卡取本机配置的IP
                            }
                        }
                    }
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String strIp : ips) {
                if (!isEmptyIP(ip)) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 获取本机的IP地址
     */
    public static String getLocalAddr() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("InetAddress.getLocalHost()-error", e);
        }
        return "";
    }


    private static boolean isEmptyIP(String ip) {
        return StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip);
    }
}
