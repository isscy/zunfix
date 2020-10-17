package cn.ff.zunfix.auth.controller;

import cn.ff.zunfix.auth.entity.vo.SendSmsCodeVo;
import cn.ff.zunfix.auth.service.LoginService;
import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.Rs;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import java.security.interfaces.RSAPublicKey;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;

/**
 *
 *
 * @author fengfan 2020/8/28
 */
@RestController
@RequestMapping("open")
@AllArgsConstructor
public class AuthOpenController {
    private LoginService loginService;
    private KeyPair keyPair;

    /**
     * 短信登陆 - 获取登陆验证码
     */
    @PostMapping("sendSmsCode")
    public R sendSmsCode(@RequestBody SendSmsCodeVo vo){
        loginService.sendSmsCode(vo.getPhone(), "");
        return Rs.ok();
    }


    /**
     * 暴露公钥的接口， 以便RSA的公钥来验证签名是否合法
     */
    @GetMapping("rsa/publicKey")
    public R getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return Rs.ok(new JWKSet(key));
    }



}
