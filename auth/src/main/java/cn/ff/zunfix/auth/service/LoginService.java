package cn.ff.zunfix.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import static cn.ff.zunfix.common.core.constant.CommonConstant.CACHE_KEY_PREFIX_SMS_LOGIN;

/**
 * _
 *
 * @author fengfan 2020/8/28
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginService {

    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * 发送短信验证码
     */
    public void sendSmsCode(String phone, String type){
        redisTemplate.opsForValue().set(CACHE_KEY_PREFIX_SMS_LOGIN + phone, "1234");
    }


    public String getSmsCodeFromRds(String phone, String type){
        String code = (String) redisTemplate.opsForValue().get(CACHE_KEY_PREFIX_SMS_LOGIN + phone);
        if (StringUtils.isBlank(code)){
            return null;
        }
        return code;
    }





}
