package cn.ff.zunfix.auth.handler.serializer;

import cn.ff.zunfix.auth.entity.TokenResult;
import cn.ff.zunfix.common.core.entity.R;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

/**
 * _
 *
 * @author fengfan 2020/10/14
 */
public class TokenResponseSerializer extends StdSerializer<TokenResult> {


    public TokenResponseSerializer() {
        super(TokenResult.class);
    }

    @Override
    public void serialize(TokenResult value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) value.getData();
        gen.writeStartObject();
        gen.writeStringField("code", value.getCode() + "");
        gen.writeStringField("message", value.getMessage());

        gen.writeObjectFieldStart("data");
        gen.writeStringField("accessToken", oAuth2AccessToken.getValue());
        gen.writeStringField("tokenType", oAuth2AccessToken.getTokenType());
        gen.writeStringField("refreshToken", oAuth2AccessToken.getRefreshToken().getValue());
        gen.writeNumberField("expiresIn", oAuth2AccessToken.getExpiresIn());
        gen.writeStringField("scope", oAuth2AccessToken.getScope().toString());
        //gen.writeStringField("company",oAuth2AccessToken.getAdditionalInformation().get("company").toString());
        gen.writeStringField("jti", oAuth2AccessToken.getAdditionalInformation().get("jti") == null ? null : oAuth2AccessToken.getAdditionalInformation().get("jti").toString());
        gen.writeStringField("id", oAuth2AccessToken.getAdditionalInformation().get("id") == null ? null : oAuth2AccessToken.getAdditionalInformation().get("id").toString());
        gen.writeStringField("userName", oAuth2AccessToken.getAdditionalInformation().get("userName") == null ? null : oAuth2AccessToken.getAdditionalInformation().get("userName").toString());
        gen.writeStringField("userId", oAuth2AccessToken.getAdditionalInformation().get("userId") == null ? null : oAuth2AccessToken.getAdditionalInformation().get("userId").toString());
        gen.writeStringField("nickName", oAuth2AccessToken.getAdditionalInformation().get("nickName") == null ? null : oAuth2AccessToken.getAdditionalInformation().get("nickName").toString());
        gen.writeEndObject();

        gen.writeEndObject();
    }

}
