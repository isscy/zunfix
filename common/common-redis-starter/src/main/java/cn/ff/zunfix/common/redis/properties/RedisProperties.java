package cn.ff.zunfix.common.redis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * _
 *
 * @author fengfan 2020/8/12
 */
@Data
@ConfigurationProperties(prefix = "zunfix.redis")
public class RedisProperties {

    private RedisItemProperties lettuce;
    private RedisItemProperties redisson;

}
