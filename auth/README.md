
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



## ~~权限微服务Debugger  ~~

1. TokenEndpoint 响应 /oauth/token 请求

- 从 principal 中获取 clientId, 进而装载 ClientDetails
- 从 parameters 中获取 clientId、scope、grantType 以组装 TokenRequest
- 校验 Client 信息
- 根据 grantType 设置 TokenRequest 的 scope
- 通过令牌授予者获取 Token。

2. TokenGranter 授予令牌过程

- 抽象实现类 AbstractTokenGranter
- getAccessToken(client, tokenRequest) 这个过程可以理解为
  - 根据 client、tokenRequest 从 OAuth2RequestFactory 中创建一个 OAuth2Request, 进而可得到 OAuth2Authentication (存放着用户的认证信息)
  - 通过 tokenService 去创建 OAuth2AccessToken (存放着用户的 token信息、过期时间)
  
3. TokenServices 的 createAccessToken

## 默认用户名密码登陆获取Token 详细流程

1. AbstractAuthenticationProcessingFilter.doFilter
    
    身份验证请求的入口，处理HTTP的Request封装成AuthenticationMananger可以处理的Authentication，并且在身份验证成功或失败之后将对应的行为转换为HTTP的Response
    
2. ClientCredentialsTokenEndpointFilter.attemptAuthentication

    是上一个过滤器(AbstractAuthenticationProcessingFilter)的实现类，默认不会开启，需要 `security.allowFormAuthenticationForClients()`开启, 用于校验匹配client_id
 
3. ProviderManager.authenticate                                                                                
     
     遍历全部的provider，调用AuthenticationProvider.authenticate进行认证

4. AbstractUserDetailsAuthenticationProvider.authenticate   

                                                                        

## 其他
1. 登陆成功后如何回调？
    ProviderManager.authenticate 中在认证成功后会 publisher 一个事件
    
    ```java
       Authentication result =  provider.authenticate(authentication);
       eventPublisher.publishAuthenticationSuccess(result); 
    ```
    
    > 注意： OAuth2AuthenticationProcessingFilter.doFilter 中对token进行check通过后也会publisher这个事件, 所以需要对事件源进行过滤
                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                          
  
