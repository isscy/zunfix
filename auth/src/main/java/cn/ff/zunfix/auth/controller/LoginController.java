package cn.ff.zunfix.auth.controller;

import cn.ff.zunfix.auth.entity.vo.SendSmsCodeVo;
import cn.ff.zunfix.auth.service.LoginService;
import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.Rs;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *
 *
 * @author fengfan 2020/8/28
 */
@RestController
@RequestMapping("open")
@AllArgsConstructor
public class LoginController {
    private LoginService loginService;


    @PostMapping("sendSmsCode")
    public R sendSmsCode(@RequestBody SendSmsCodeVo vo){
        loginService.sendSmsCode(vo.getPhone(), "");
        return Rs.ok();
    }


    @GetMapping("smsCode/{phone}")
    public R smsCode(@PathVariable String phone){
        String s = loginService.getSmsCodeFromRds(phone, "");
        return Rs.ok(s);
    }
}
