package cn.ff.zunfix.auth.mapper;

import cn.ff.zunfix.common.security.entity.SysOauthClientDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOauthClientDetailsMapper {

    SysOauthClientDetails getByClientId(String clientId);
}
