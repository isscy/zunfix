package cn.ff.zunfix.common.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    private TokenProperties token = new TokenProperties();

    private Map<String, List<String>> unCheckUrl = new HashMap<>();

    /**
     * 获取无需鉴权的URL数组  此时是不需要模块key的
     */
    public String[] unCheckUrlArray() {
        List<String> urls = //unCheckUrl.entrySet().stream().map(Map.Entry::getValue).flatMap(List::stream).collect(Collectors.toList());
                unCheckUrl.values().stream().flatMap(List::stream).collect(Collectors.toList());
        String[] arr = new String[unCheckUrl.size()];
        return urls.toArray(arr);
    }
}
