package cn.ff.zunfix.auth.entity;

import cn.ff.zunfix.auth.handler.serializer.TokenResponseSerializer;
import cn.ff.zunfix.common.core.entity.R;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * _
 *
 * @author fengfan 2020/10/15
 */

@JsonSerialize(using = TokenResponseSerializer.class)
public class TokenResult<T> extends R<T> {





    public TokenResult(R<T> r){
        this.setCode(r.getCode());
        this.setData(r.getData());
        this.setMessage(r.getMessage());
    }





}
