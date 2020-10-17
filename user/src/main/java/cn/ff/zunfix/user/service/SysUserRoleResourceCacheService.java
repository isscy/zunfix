package cn.ff.zunfix.user.service;

import cn.ff.zunfix.common.core.constant.CommonConstant;
import cn.ff.zunfix.common.core.entity.SysResource;
import cn.ff.zunfix.common.core.exception.InnerException;
import cn.ff.zunfix.common.redis.utils.RedisUtil;
import cn.ff.zunfix.common.security.constant.SecurityConstant;
import cn.ff.zunfix.common.security.mapper.SysResourceMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fengfan 2020/9/10
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserRoleResourceCacheService {
    /*private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;*/
    private final SysResourceMapper sysResourceMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisUtil redisUtil;


    /**
     * 创建 url-roles map 的缓存
     * key -> auth:resourceRolesMap   hashKey -> method:serverId:url  hashValue -> role的List
     * hashKey 的 约定 ： 大写的请求方法   微服务ID  不以/开头的URL
     */
    public Map<String, List<String>> initResourceRolesMap() {
        List<SysResource> list = sysResourceMapper.getResourceRolesCacheRel();
        TreeMap<String, List<String>> map = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().map(e -> new Rrr(e.getMethod(), e.getServerId(), e.getUrl(), e.getOfRoles()))
                .sorted(Comparator.comparing(Rrr::getUrl)).collect(Collectors.toMap(Rrr::getHashKey, Rrr::getOfRoles, (k1, k2) -> k1, TreeMap::new));
        redisUtil.hSetAll(SecurityConstant.CACHE_RESOURCE_ROLES_MAP_KEY, map);
        return map;
    }

    private void refreshResourceRolesMap() {
        redisUtil.del(SecurityConstant.CACHE_RESOURCE_ROLES_MAP_KEY);
    }

    public Map<String, List<String>> getResourceRolesMap() {
        Map<String, List<String>> map = new TreeMap<>();
        BoundHashOperations<String, String, List<String>> boundHashOps = redisTemplate.boundHashOps(SecurityConstant.CACHE_RESOURCE_ROLES_MAP_KEY);
        try (Cursor<Map.Entry<String, List<String>>> cursor = boundHashOps.scan(ScanOptions.NONE)) {
            while (cursor.hasNext()) {
                Map.Entry<String, List<String>> entry = cursor.next();
                map.put(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            log.error("从redis中获取ResourceRolesMap失败!  {}", e.getMessage());
            e.printStackTrace(); // 不处理直接返回空白map
            return null;
        }
        return map.isEmpty() ? initResourceRolesMap() : map;
    }

    public List<String> getResourceRolesMap(String hashKey) {
        return (List<String>) redisUtil.hGet(SecurityConstant.CACHE_RESOURCE_ROLES_MAP_KEY, hashKey);
    }



    public String spliceResourceHashKey(String method, String url) {
        return method + CommonConstant.NORM_DELIMITER + url;
    }

    public String[] splitResourceHashKey(String hashKey) {
        return hashKey.split(CommonConstant.NORM_DELIMITER);
    }


    /**
     * resourceRoleRel beanClass
     */
    @Data
    static class Rrr {

        private String method;
        private String serverId;
        private String url;
        private String hashKey;
        private List<String> ofRoles = new ArrayList<>();

        private Rrr() {
        }

        public Rrr(String method, String serverId, String url) {
            this.method = method;
            this.serverId = serverId;
            this.url = url;
            if (StringUtils.isBlank(url)) {
                throw new InnerException("url不能为空");
            }
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.isBlank(method) ? "" : method).append(CommonConstant.NORM_DELIMITER);
            sb.append(StringUtils.isBlank(serverId) ? "" : serverId).append(CommonConstant.NORM_DELIMITER);
            sb.append(url.startsWith("/") ? url.substring(1) : url);
            this.hashKey = sb.toString();
        }

        public Rrr(String method, String serverId, String url, List<String> ofRoles) {
            this(method, serverId, url);
            this.ofRoles = ofRoles;
        }

        public Rrr(String method, String serverId, String url, String ofRoles) {
            this(method, serverId, url);
            if (StringUtils.isBlank(ofRoles)) {
                this.ofRoles = new ArrayList<>();
            } else {
                this.ofRoles = Arrays.asList(ofRoles.split(","));
            }
        }
    }

}


