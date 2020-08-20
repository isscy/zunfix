package cn.ff.zunfix.auth.provider.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * _
 *
 * @author fengfan 2020/8/18
 */
@Data
@EqualsAndHashCode(callSuper = true)
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


    @Override
    public Object getPrincipal() {
        return principal;
    }

    /*@Override
    public void setDetails(Object details) {
        super.setDetails(details);
    }*/
}
