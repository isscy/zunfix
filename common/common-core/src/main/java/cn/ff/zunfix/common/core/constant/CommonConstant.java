package cn.ff.zunfix.common.core.constant;

/**
 * 基础公用常量类
 *
 * @author fengfan 2020/4/12
 */
public interface CommonConstant {

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
}
