package cn.ff.zunfix.common.security.properties;

import lombok.Data;

@Data
public class TokenProperties {
    private String secret;
    private long expired;
    private long refreshExpired;
}
