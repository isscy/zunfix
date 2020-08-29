package cn.ff.zunfix.auth.provider.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * _
 *
 * @author fengfan 2020/8/18
 */

public abstract class BasisAuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 登陆类别
     */
    protected String loginType;
    /**
     * 验证码
     */
    protected String captchaCode;
    /**
     * 验证码类别 ： 干扰图片 、 滑动拼图
     */
    protected String captchaType;
    /**
     * 失效时间
     */
    protected long expiration;

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 在验证之前封装电话号、用户名
     * 在验证之后存储 用户信息
     */
    protected Object principal;

    public BasisAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public BasisAuthenticationToken() {
        super(null);
    }

    public abstract void setDetailPlus(Authentication authentication);


    @Override
    public Object getPrincipal() {
        return principal;
    }

    /*@Override
    public void setDetails(Object details) {
        super.setDetails(details);
    }*/

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        this.captchaType = captchaType;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }
}
