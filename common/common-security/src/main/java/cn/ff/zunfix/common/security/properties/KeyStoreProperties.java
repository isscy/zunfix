package cn.ff.zunfix.common.security.properties;

import lombok.Data;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * _
 *
 * @author fengfan 2020/9/1
 */
@Data
public class KeyStoreProperties {

    private String location;
    private String secret;

    private String password;
    private String alias;
    private String type = "jks";


    public Resource getKeyResource() {
        if (this.location == null){
            return null;
        }
        return new ClassPathResource(location);
    }

    public char[] getSecretChars(){
        if (this.secret == null){
            return null;
        }
        return this.secret.toCharArray();
    }

    public char[] getPasswordChars(){
        if (this.password == null){
            return null;
        }
        return this.password.toCharArray();
    }
}
