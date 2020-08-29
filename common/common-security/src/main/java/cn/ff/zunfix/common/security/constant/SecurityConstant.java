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
     * 角色前缀
     */
    public final static String ROLE = "ROLE_";

    /**
     * 默认最基本的角色 自动为每个登陆成功的用户加上此角色
     */
    public final static String ROLE_FULLY = "ROLE_FULLY";


    /**
     * 请求参数
     */
    public final static String PARAMETER_LOGIN_TYPE = "login_type";


    /**
     * 登陆方式loginType / 授权方式grantType
     */
    public final static String LOGIN_TYPE_PASSWORD = "password"; // 用户名密码登陆
    public final static String LOGIN_TYPE_SMS = "sms"; // 手机号验证码登陆
    public final static String LOGIN_TYPE_QR = "qr"; // 扫码登陆
    public final static String LOGIN_TYPE_REFRESH = "refresh_new"; // 刷新token
    public final static List<String> LOGIN_TYPES = Arrays.asList(LOGIN_TYPE_PASSWORD, LOGIN_TYPE_QR, LOGIN_TYPE_SMS, LOGIN_TYPE_REFRESH); // 扫码登陆


}
