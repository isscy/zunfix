package cn.ff.zunfix.common.security.service;

import cn.ff.zunfix.common.core.constant.CommonConstant;
import cn.ff.zunfix.common.core.entity.SysUser;
import cn.ff.zunfix.common.core.utils.Json;
import cn.ff.zunfix.common.security.entity.vo.BaseRequestVo;
import cn.ff.zunfix.common.security.properties.Oauth2Properties;
import com.nimbusds.jose.JWSObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.util.AntPathMatcher;

import java.text.ParseException;
import java.util.*;

/**
 * 对请求进行鉴权
 * 当前规则 ： 具有超级管理员角色的全部放过
 *
 * @author fengfan 2020/9/30
 */
public abstract class PermissionService {

    @Autowired
    private Oauth2Properties oauth2Properties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();


    public boolean hasPermission(Authentication authentication, String requestMethod, String requestUri) {
        //TODO;
        return false;
    }


    public boolean hasPermission(String token, String requestMethod, String requestPath, String prefixPath, String realPath, HttpHeaders headers) {
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(requestMethod)) {
            return true;
        }
        if (oauth2Properties.ofUnCheckUrl(prefixPath, realPath)){//白名单路径直接放行
            return true;
        }
        /*for (String ignoreUrl : oauth2Properties.unCheckUrlList()) { //白名单路径直接放行
            if (pathMatcher.match(ignoreUrl, requestPath)) {
                return true;
            }
        }*/
        if (headers.get(CommonConstant.GATEWAY_HEADER_FROM) != null && headers.get(CommonConstant.GATEWAY_HEADER_FROM).stream().anyMatch(CommonConstant.GATEWAY_HEADER_FROM_IN::equalsIgnoreCase)) {
            return true;
        }
        SysUser user;
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            String userStr = jwsObject.getPayload().toString();
            user = Json.parseObject(userStr, SysUser.class);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        if (user == null || StringUtils.isBlank(user.getId())) {
            return false;
        }
        if (CommonConstant.SUPER_ADMIN_USER_ID.equals(user.getId())) {
            return true;
        }
        Map<String, List<String>> resourceRolesMap = getResourceRolesMap();
        List<String> requiredAuthorities = new ArrayList<>(); // 所有有权限访问此URL的角色
        for (String key : resourceRolesMap.keySet()) {
            String[] keys = key.split(CommonConstant.NORM_DELIMITER);
            if (keys[0].equalsIgnoreCase(requestMethod) && pathMatcher.match(keys[2], requestPath)) {
                List<String> roles = resourceRolesMap.get(key);
                if (roles == null || roles.isEmpty()) { // 如果此URL无需鉴权 直接放过
                    return true;
                } else {
                    requiredAuthorities.addAll(roles);
                }
            }
        }
        List<String> userOwnRoles; // 当前用户拥有的角色列表
        if (StringUtils.isBlank(user.getRolesString())) {
            userOwnRoles = getUserHadRoles(user.getId());
        } else {
            userOwnRoles = Arrays.asList(user.getRolesString().split(","));
        }
        if (userOwnRoles == null || userOwnRoles.isEmpty()) {
            userOwnRoles = new ArrayList<>();
        }
        return Collections.disjoint(userOwnRoles, requiredAuthorities);

    }

    public boolean hasPermission(BaseRequestVo vo) {
        return hasPermission(vo.getToken(), vo.getMethod(), vo.getFullPath(), vo.getPrefixPath(), vo.getRealPath(), vo.getHeaders());
    }

    /**
     * 获取资源对应的角色
     */
    protected abstract Map<String, List<String>> getResourceRolesMap();

    /**
     * 获取一个用户拥有的全部角色Code
     */
    protected abstract List<String> getUserHadRoles(String userId);

}
