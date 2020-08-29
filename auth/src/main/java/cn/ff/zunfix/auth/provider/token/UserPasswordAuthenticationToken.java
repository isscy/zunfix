package cn.ff.zunfix.auth.provider.token;

import cn.ff.zunfix.common.security.constant.SecurityConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * 用户名密码登陆 的 token
 *
 * @author fengfan 2020/8/17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPasswordAuthenticationToken extends BasisAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String username;
    private String password;


    public UserPasswordAuthenticationToken(String username) {
        super(null);
        super.loginType = SecurityConstant.LOGIN_TYPE_PASSWORD;
        super.principal = this.username = username;
        setAuthenticated(false);
    }


    public UserPasswordAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.loginType = SecurityConstant.LOGIN_TYPE_PASSWORD;
        super.principal = principal;
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return password;
    }

    /*@Override
    public Object getPrincipal() {
        return super.principal;
    }*/

    /**
     * 在Provider中new出新的token会丢失之前的  所以加上
     */
    @Override
    public void setDetailPlus(Authentication authentication){
        this.setDetails(authentication.getDetails());
        UserPasswordAuthenticationToken old = (UserPasswordAuthenticationToken)authentication;
        this.setClientId(old.getClientId());
        this.setClientSecret(old.getClientSecret());
        /*this.setPrincipal(old.getPrincipal());
        this.setCredentials(old.getCredentials());*/
    }



}
