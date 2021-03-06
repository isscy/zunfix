package cn.ff.zunfix.common.core.entity;

import cn.ff.zunfix.common.core.utils.Rs;
import lombok.Data;

import java.io.Serializable;

/**
 * Results
 * @author fengfan 2020/8/7
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    public R() {}

    public R(Integer code) {
        this.code = code;
    }

    public R(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess(){
        return Rs.successCode.equals(code);
    }
    public boolean isSuccess(boolean needHadValue){
        if (needHadValue){
            return isSuccess() && data != null;
        }
        return isSuccess();
    }
}
