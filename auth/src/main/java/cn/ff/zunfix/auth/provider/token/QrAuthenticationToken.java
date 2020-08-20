package cn.ff.zunfix.auth.provider.token;

import cn.ff.zunfix.common.security.constant.SecurityConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 扫码登陆 的 token
 *
 * @author fengfan 2020/8/17
 */
public class QrAuthenticationToken extends BasisAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String ticketId;
    private String userId;
    private String code;


    public QrAuthenticationToken(String ticketId) {
        super(null);
        super.loginType = SecurityConstant.LOGIN_TYPE_PASSWORD;
        super.principal = this.ticketId = ticketId;
        setAuthenticated(false);
    }


    public QrAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        super.loginType = SecurityConstant.LOGIN_TYPE_PASSWORD;
        super.principal = principal;
        setAuthenticated(true);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCode() {
        return code;
    }
}
