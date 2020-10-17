package cn.ff.zunfix.gateway.component;

import cn.ff.zunfix.common.core.entity.SysUser;
import cn.ff.zunfix.common.core.utils.Json;
//import cn.ff.zunfix.auth.properties.Oauth2Properties;
import cn.ff.zunfix.common.security.entity.vo.BaseRequestVo;
import cn.ff.zunfix.common.security.service.impl.RemotePermissionService;
import cn.ff.zunfix.gateway.utils.GatewayBaseHelper;
import com.nimbusds.jose.JWSObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * _
 *
 * @author fengfan 2020/9/4
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    //    private RedisTemplate<String, Object> redisTemplate;
    //private final Oauth2Properties oauth2Properties;
    //private ReactiveRedisTemplate reactiveRedisTemplate;
    //private SysUserRoleResourceCacheService sysUserRoleResourceCacheService;
    //private final IUserRemote iUserRemote;
    private final RemotePermissionService remotePermissionService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String requestPath = request.getURI().getPath();
        String token = GatewayBaseHelper.getTokenFromReq(request);
        BaseRequestVo vo = new BaseRequestVo(request.getMethodValue(), requestPath, token, request.getHeaders());
        boolean rst = remotePermissionService.hasPermission(vo);
        return Mono.just(new AuthorizationDecision(rst));
    }

    /**
     * @deprecated
     */
    //@Override
    public Mono<AuthorizationDecision> check2(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String requestPath = request.getURI().getPath();
        String method = request.getMethodValue();
        PathMatcher pathMatcher = new AntPathMatcher();
        /*for (String ignoreUrl : oauth2Properties.unCheckUrlList()) { //白名单路径直接放行
            if (pathMatcher.match(ignoreUrl, requestPath)) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }*/
        if (request.getMethod() == HttpMethod.OPTIONS) { //对应跨域的预检请求直接放行
            return Mono.just(new AuthorizationDecision(true));
        }
        mono.map(authentication -> {
            System.out.println(authentication);
            return authentication;
        });
        // 获取token
        String token = GatewayBaseHelper.getTokenFromReq(request);
        if (StringUtils.isBlank(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }
        SysUser user;
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            String userStr = jwsObject.getPayload().toString();
            user = Json.parseObject(userStr, SysUser.class);
        } catch (ParseException e) {
            e.printStackTrace();
            return Mono.just(new AuthorizationDecision(false));
        }
        if (user == null || StringUtils.isBlank(user.getId())) {
            return Mono.just(new AuthorizationDecision(false));
        }
        /*R<Map<String, List<String>>> r = iUserRemote.getResourceRolesRel();
        if (r.isSuccess(true)) {
            Map<String, List<String>> data = r.getData();



        }*/
        //Map<String, List<String>> resourceRolesMap = sysUserRoleResourceCacheService.getResourceRolesMap();
        //resourceRolesMap.entrySet().stream().forEach();
        //Map<Object, Object> resourceRolesMap =  reactiveRedisTemplate.opsForHash().entries(SecurityConstant.CACHE_RESOURCE_ROLES_MAP_KEY);
        return Mono.just(new AuthorizationDecision(true));
        //不同用户体系登录不允许互相访问
        /*if ()
        try {
            String token = request.getHeaders().getFirst(SecurityConstant.JWT_TOKEN_PREFIX);
            if(StrUtil.isEmpty(token)){
                return Mono.just(new AuthorizationDecision(false));
            }
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();
            UserDto userDto = JSONUtil.toBean(userStr, UserDto.class);
            if (AuthConstant.ADMIN_CLIENT_ID.equals(userDto.getClientId()) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(false));
            }
            if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(false));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return Mono.just(new AuthorizationDecision(false));
        }
        //非管理端路径直接放行
        if (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
            return Mono.just(new AuthorizationDecision(true));
        }
        //管理端路径需校验权限
        Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
        Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
        List<String> authorities = new ArrayList<>();
        while (iterator.hasNext()) {
            String pattern = (String) iterator.next();
            if (pathMatcher.match(pattern, uri.getPath())) {
                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
            }
        }
        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }*/

    }


}
