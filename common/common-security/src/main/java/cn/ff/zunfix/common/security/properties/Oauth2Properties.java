package cn.ff.zunfix.common.security.properties;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * _
 *
 * @author fengfan 2020/8/19
 */
@Data
@Component
@ConfigurationProperties(prefix = "zunfix.security")
public class Oauth2Properties {


    private KeyStoreProperties keyStore = new KeyStoreProperties();

    private TokenProperties token = new TokenProperties();
    // TODO 拆成 分微服务的
    private Map<String, List<String>> unCheckUrl = new HashMap<>();

    /**
     * 获取无需鉴权的URL数组  此时是不需要模块key的
     */
    @Deprecated
    public String[] unCheckUrlArray() {
        List<String> urls = //unCheckUrl.entrySet().stream().map(Map.Entry::getValue).flatMap(List::stream).collect(Collectors.toList());
                unCheckUrl.values().stream().flatMap(List::stream).collect(Collectors.toList());
        String[] arr = new String[unCheckUrl.size()];
        return urls.toArray(arr);
    }

    /*public List<String> unCheckUrlList() {
        return unCheckUrl.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }*/


    /*public List unCheckUrlAsList(boolean isAddPrefix){
        Function<Map.Entry<String, List<String>>, Map.Entry<String, List<String>>> supplier = e -> {
            List<String> list = new ArrayList<>();
            if (isAddPrefix){
                if ()
            }else {
                list = e.getValue();
            }
        };
        unCheckUrl.entrySet().stream().map(e -> e.)

    }*/

    public boolean ofUnCheckUrl(String serverId, String realPath) { // 忽略请求方法！
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> unCheckUrls = new ArrayList<>();
        if (StringUtils.isBlank(serverId)) {
            unCheckUrls = get("/");
        } else {
            unCheckUrls.addAll(get("*"));
            unCheckUrls.addAll(get(serverId));
        }
        return unCheckUrls.stream().anyMatch(e -> pathMatcher.match(e, realPath));
    }


    private List<String> get(String key){
        return Optional.ofNullable(this.unCheckUrl.get(key)).orElse(new ArrayList<>());

    }

}
