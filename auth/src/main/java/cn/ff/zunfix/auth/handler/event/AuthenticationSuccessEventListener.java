package cn.ff.zunfix.auth.handler.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 对 登陆成功 和 token验证通过的事件 进行处理
 * 详见auth模块的readme.md
 * @author fengfan 2020/8/26
 */
@Slf4j
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {



    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        log.error("触发事件：" + event.getSource().getClass().getName());
        //这里的事件源除了登录事件（UsernamePasswordAuthenticationToken）还有可能是token验证事件源（OAuth2Authentication）
        if("org.springframework.security.authentication.UsernamePasswordAuthenticationToken".equals(event.getSource().getClass().getName())){
            log.error("TOKEN验证：" + event.getSource().getClass().getName());
        }

        //这里还有oAuth2的客户端认证的事件，需要做一个判断
        if(event.getAuthentication().getDetails() != null){
            log.error("登陆成功 ：" + event.getSource().getClass().getName());
        }
    }

}
