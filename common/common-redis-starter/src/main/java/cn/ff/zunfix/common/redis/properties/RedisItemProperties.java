package cn.ff.zunfix.common.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * _
 *
 * @author fengfan 2020/8/12
 */
@Data
public class RedisItemProperties {

    /**
     * 是否开启
     */
    private Boolean enable = true;
}
