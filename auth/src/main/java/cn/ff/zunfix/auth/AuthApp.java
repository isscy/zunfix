package cn.ff.zunfix.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * _
 *
 * @author fengfan 2020/8/10
 */
@MapperScan(basePackages = {"cn.ff.zunfix.auth.mapper"})
@SpringCloudApplication
public class AuthApp {


    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }

    /*

    todo 资源中心 单独放到common模块中 可以在每个微服务中引入
    todo 成功和失败的处理
    todo 验证码
     */




}
