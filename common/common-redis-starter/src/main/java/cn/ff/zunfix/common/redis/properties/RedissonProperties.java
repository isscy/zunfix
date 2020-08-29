package cn.ff.zunfix.common.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * _
 *
 * @author fengfan 2020/8/12
 */
//@ConfigurationProperties(prefix = "spring.redis.redisson")
@Deprecated
public class RedissonProperties {


    private String config;
    private String enable;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
