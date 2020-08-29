package cn.ff.zunfix.auth.provider.token;

import cn.ff.zunfix.common.security.constant.SecurityConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * 短信登陆 的 token
 *
 * @author fengfan 2020/8/17
 */
@NoArgsConstructor
public class SmsAuthenticationToken extends BasisAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String phone;
    private String code;


    public SmsAuthenticationToken(String phone) {
        super(null);
        super.loginType = SecurityConstant.LOGIN_TYPE_PASSWORD;
        super.principal = this.phone = phone;
        setAuthenticated(false);
    }


    public SmsAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.loginType = SecurityConstant.LOGIN_TYPE_PASSWORD;
        super.principal = principal;
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    /*@Override
    public Object getPrincipal() {
        return super.principal;
    }*/
    @Override
    public void setDetailPlus(Authentication authentication){
        this.setDetails(authentication.getDetails());
        SmsAuthenticationToken old = (SmsAuthenticationToken)authentication;
        this.setClientId(old.getClientId());
        this.setClientSecret(old.getClientSecret());
    }
    public void setDetailPlus(Object details, ClientDetails client){
        this.setDetails(details);
        this.setClientId(client.getClientId());
        this.setClientSecret(client.getClientSecret());
    }


    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
