package cn.ff.zunfix.auth.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * _
 *
 * @author fengfan 2020/8/28
 */
@Data
public class SendSmsCodeVo implements Serializable {



    private String ticketId;
    private String phone;
    // 登陆 注册 忘记密码
    private String type;
    // 验证码
    private String verifyCode;
    // 验证码类型
    private String verifyType;
}
