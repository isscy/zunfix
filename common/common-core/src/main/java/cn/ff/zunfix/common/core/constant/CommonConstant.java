package cn.ff.zunfix.common.core.constant;

/**
 * 基础公用常量类
 *
 * @author fengfan 2020/4/12
 */
public interface CommonConstant {


    String SUPER_ADMIN_USER_ID = "1";


    String NORM_DELIMITER = ":";

    /**
     * 删除标记
     */
    String DEL_NORMAL = "0"; // 正常
    String DEL_YES = "1"; // 已逻辑删除

    /**
     * header 路由附加key
     */
    String GATEWAY_HEADER_SERVER = "x-server-id"; // 所属微服务
    String GATEWAY_HEADER_TOKEN = "x-user-token"; // 用户token
    String GATEWAY_HEADER_CLIENT = "x-client-id"; // 来源客户端
    String GATEWAY_HEADER_FROM = "x-request-source"; // 请求来源key ： OUT-来自外部的 IN-内部服务间调用

    String GATEWAY_HEADER_FROM_IN = "IN"; // 请求来源value ： IN-内部服务间调用
    String GATEWAY_HEADER_FROM_OUT = "OUT"; // 请求来源value ： OUT-来自外部的



    /**
     * redis 缓存 key的前缀
     */
    String CACHE_KEY_PREFIX_SMS_LOGIN = "sms_code_login:"; // 短信登陆
    String CACHE_KEY_PREFIX_SMS_REGISTER = "sms_code_register:";// 短信注册
    String CACHE_KEY_PREFIX_SMS_RESET = "sms_code_resetPwd:";// 短信重置密码

    /**
     * redis 缓存 map
     */
    String CACHE_MAP_USER_INFO = "userInfoMap";
}
