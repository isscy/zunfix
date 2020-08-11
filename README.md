

# _zunfix快速开发平台_

基于Spring Cloud Hoxton.RELEASE 的快速开发平台, 集成了Nacos作为注册和配置中心、oauth2鉴权、Seata分布式事务、基于RBAC的操作权限以及数据权限、通知权限(阿里短信、极光APP推送、邮件通知)、Excel导入导出封装等

## 1 技术选型

|类别                | 框架          
--------------     |----------- 
| 核心框架             |  springcloud Hoxton.RELEASE |
| 安全框架             |   Spring Security、 Spring Cloud Oauth2     |
| 持久层框架           | MyBatis-Plus        |
| 数据库连接池         | Alibaba Druid       |
| 消息队列             | RabbitMQ     |
| 缓存中间件             | Redis (Redisson)     |
| 分布式任务调度        | elastic-job       |
| 分布式事务        | Seata       |
    


## 2 技术栈及项目结构说明

```
cloudPlatform
├── auth -- 认证服务
├── common -- 基础公共模块
     ├── core -- 最基础模块，包含通用工具类、Redis、用户权限Entity和feign接口
     └── security -- 权限和oauth2权限配置
├── gateway -- 网关 [8888]
├── admin -- 后台管理模块
├── order -- 订单服务 -示例
└── visual  -- 图形化模块 TODO
     ├── monitor -- 微服务监控
     └── codegen -- 图形化代码生成
	 
```


## 3 约定

### 3.1 security及oauth2相关
1. 不再拆分单独的认证中心和授权中心，更没在单独微服务中进行Resource鉴权，以后可按需扩充
2. 角色识别码必须以 ROLE_开头！ 例如 普通业务员角色 ROLE_BIZ

### 3.2 异常
1. 异常封装了两类：
```
  BaseException - 业务异常基础类
  AuthException - 认证、鉴权等异常
```
2. 所有的异常应该继承于以上两个，现有的继承类如下：
```
  BaseException 继承类：
      -> NoTraceBizException 不打印堆栈的异常， 例如请求方法、请求参数错误这种，没必要打印出一大坨堆栈来
```

## 4 权限详情

1. 生成密钥

```shell
# 创建
keytool -genkeypair -alias zunfix -keyalg RSA -keypass zunfix -keystore zunfix.jks -storepass zunfix
# 生成公钥
keytool -list -rfc --keystore zunfix.jks | openssl x509 -inform pem -pubkey
```
私钥加密生成令牌 公钥放在其他微服务中校验令牌合法性

2. 实现UserDetailsService接口，用于加载用户信息

创建 `cn.ff.zunfix.auth.service.UserDetailsServiceImpl` 用于加载用户信息(以及用户拥有的角色)


---
