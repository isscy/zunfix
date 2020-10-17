package cn.ff.zunfix.common.redis.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * _
 *
 * @author fengfan 2020/9/10
 */
@Component
@AllArgsConstructor
public class RedisUtil {


    private RedisTemplate<String, Object> redisTemplate;
    /*
     *  ----------------------------------- String -------------------------------------------
     */

    // 字符串是否存在
    public boolean hasKey(String key) {
        Boolean b = redisTemplate.hasKey(key);
        return b == null ? false : b;
    }

    // 删除
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /*
     *  ----------------------------------- Map -------------------------------------------
     */

    /**
     * 获取hash中的一个
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     */
    public Map<String, Object> hmGet(String key) {
        HashOperations<String, String, Object> hps = redisTemplate.opsForHash();
        return hps.entries(key);
    }
    /**
     * 向hash表中放入数据
     */
    public void hSetAll(String key, Map<String, ?> map) {
        this.del(key);
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向hash表中放入数据
     */
    public boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除hash表中的值
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 获取map 里所有的 key 在少数据量下用这个
     */
    public Set<String> hKeys(String key) {
        Set<Object> keys = redisTemplate.opsForHash().keys(key);
        return keys.stream().map(String::valueOf).collect(Collectors.toSet());
        //return Optional.ofNullable(keys).orElse(new HashSet<>()).stream().map(String::valueOf).collect(Collectors.toSet()); // keys不会是null
    }

    /**
     * 获取map 里所有的 key 在大数据量下用
     */
    public Set<String> hScan(String key) {
        Set<String> result = new HashSet<>();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            result.add(entry.getKey().toString());
        }
        return result;
    }

    /*
     * 获取hash表中存在的所有的值
     */
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

}
