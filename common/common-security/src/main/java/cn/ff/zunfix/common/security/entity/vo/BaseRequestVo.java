package cn.ff.zunfix.common.security.entity.vo;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * _
 *
 * @author fengfan 2020/10/14
 */
@Data
public class BaseRequestVo {

    private String method;
    private String token;
    /**
     * 请求全路径  =  prefixPath  +  "/"  + realPath
     */
    private String fullPath;
    private String prefixPath;
    private String realPath;
    // 请求头
    private HttpHeaders headers;

    public BaseRequestVo() {
    }

    public BaseRequestVo(String method, String fullPath, String token, HttpHeaders headers) {
        this.method = method;
        this.fullPath = fullPath;
        this.token = token;
        this.headers = headers;
        this.prefixPath = Arrays.stream(StringUtils.tokenizeToStringArray(fullPath, "/")).findFirst().orElse("");
        this.realPath = Arrays.stream(StringUtils.tokenizeToStringArray(fullPath, "/")).skip(1L).collect(Collectors.joining("/"));

    }
}
