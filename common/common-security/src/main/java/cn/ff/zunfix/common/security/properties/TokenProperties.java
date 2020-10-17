package cn.ff.zunfix.common.security.properties;

import lombok.Data;

@Data
public class TokenProperties {
    private String secret;
    private long expired;
    private long refreshExpired;

    /**
     *  是否续签
     */
    private Boolean renewEnable = false;

    /**
     * 续签时间比例，当前剩余时间小于小于过期总时长的 x 则续签
     */
    private Double renewTimeRatio = 0.5;
}
