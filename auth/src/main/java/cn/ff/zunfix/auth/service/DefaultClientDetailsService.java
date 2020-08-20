package cn.ff.zunfix.auth.service;

import cn.ff.zunfix.auth.mapper.SysOauthClientDetailsMapper;
import cn.ff.zunfix.common.security.entity.SysOauthClientDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * 获取客户端
 *
 * @author fengfan 2020/5/12
 */
@Service
@AllArgsConstructor
public class DefaultClientDetailsService implements ClientDetailsService {

    private final SysOauthClientDetailsMapper sysOauthClientDetailsMapper;

    @Override
    public SysOauthClientDetails loadClientByClientId(String clientId)
            throws ClientRegistrationException {
        SysOauthClientDetails clientDetails = sysOauthClientDetailsMapper.getByClientId(clientId);
        if (clientDetails == null) {
            throw new ClientRegistrationException("客户端[" + clientId + "]不存在");
        }
        return clientDetails;
    }
}
