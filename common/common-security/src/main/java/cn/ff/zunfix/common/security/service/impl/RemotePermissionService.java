package cn.ff.zunfix.common.security.service.impl;

import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.security.service.PermissionService;
import cn.ff.zunfix.common.security.service.feign.IUserRemote;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * _
 *
 * @author fengfan 2020/10/12
 */
@Slf4j
public class RemotePermissionService extends PermissionService {
    private IUserRemote iUserRemote;

    public RemotePermissionService(IUserRemote iUserRemote) {
        this.iUserRemote = iUserRemote;
    }

    @Override
    protected Map<String, List<String>> getResourceRolesMap() {
        R<Map<String, List<String>>> result = iUserRemote.getResourceRolesRel();
        if (!result.isSuccess(true)) {
            log.error(result.getMessage());
            return null;
        }
        return result.getData();
    }

    @Override
    protected List<String> getUserHadRoles(String userId) {
        R<List<String>> result = iUserRemote.getUserOwnRoleCodes(userId);
        if (!result.isSuccess(true)) {
            log.error(result.getMessage());
            return null;
        }
        return result.getData();
    }
}
