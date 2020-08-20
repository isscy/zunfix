
## 权限微服务开发流程

1. 生成密钥

```shell
# 创建
keytool -genkeypair -alias zunfix -keyalg RSA -keypass zunfix -keystore zunfix.jks -storepass zunfix
# 生成公钥
keytool -list -rfc --keystore zunfix.jks | openssl x509 -inform pem -pubkey
```
私钥加密生成令牌 公钥放在其他微服务中校验令牌合法性

2. 实现UserDetailsService接口，用于加载用户信息

创建`cn.ff.zunfix.common.security.entity.BaseUserDetail`封装用户信息实体类

创建 `cn.ff.zunfix.auth.service.UserDetailsServiceImpl` 类用于加载用户信息(以及用户拥有的角色)

3. 添加oAuth2认证和资源服务器配置

创建`cn.ff.zunfix.auth.config.AuthorizationServerConfig`认证服务器配置类

创建`cn.ff.zunfix.auth.config.AuthorizationServerConfig`认证服务器配置类

创建`cn.ff.zunfix.auth.config.WebSecurityConfig`权限配置类 

4. token配置

创建`cn.ff.zunfix.auth.config.JwtTokenStoreConfig`来处理token的存储
