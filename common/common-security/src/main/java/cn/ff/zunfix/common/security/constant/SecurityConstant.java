package cn.ff.zunfix.common.security.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 权限相关的常量类和全局方法
 *
 * @author fengfan 2020/8/11
 */
public class SecurityConstant {

    /**
     * redis中授权token对应的key
     */
    public final static String REDIS_TOKEN_AUTH = "auth:";
    /**
     * redis中应用对应的token集合的key
     */
    public final static String REDIS_CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    /**
     * redis中用户名对应的token集合的key
     */
    public final static String REDIS_UNAME_TO_ACCESS = "uname_to_access:";

    /**
     * 角色前缀
     */
    public final static String ROLE = "ROLE_";

    /**
     * 默认最基本的角色 自动为每个登陆成功的用户加上此角色
     */
    public final static String ROLE_FULLY = "ROLE_FULLY";

    /**
     * 认证信息Http请求头
     */
    public final static String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    public final static String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 请求参数
     */
    public final static String PARAMETER_LOGIN_TYPE = "login_type"; // 登陆类别
    public final static String PARAMETER_TOKEN_TYPE = "token_type";// token类型 ： JWT 、 UUID 、 Authentication

    /**
     * 登陆方式loginType / 授权方式grantType
     */
    public final static String LOGIN_TYPE_PASSWORD = "password"; // 用户名密码登陆
    public final static String LOGIN_TYPE_SMS = "sms"; // 手机号验证码登陆
    public final static String LOGIN_TYPE_QR = "qr"; // 扫码登陆
    public final static String LOGIN_TYPE_REFRESH = "refresh_new"; // 刷新token
    public final static List<String> LOGIN_TYPES = Arrays.asList(LOGIN_TYPE_PASSWORD, LOGIN_TYPE_QR, LOGIN_TYPE_SMS, LOGIN_TYPE_REFRESH); // 扫码登陆

    /**
     * redis 缓存key
     */
    public final static String CACHE_RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";
    public final static String CACHE_USER_DETAIL_KEY = "auth:userDetailMap";

}
