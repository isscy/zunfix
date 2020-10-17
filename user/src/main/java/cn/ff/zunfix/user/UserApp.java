package cn.ff.zunfix.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * _
 *
 * @author fengfan 2020/9/10
 */
@MapperScan(basePackages = {"cn.ff.zunfix.user.mapper", "cn.ff.zunfix.common.security.mapper"})
@SpringCloudApplication
public class UserApp {


    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }

}
