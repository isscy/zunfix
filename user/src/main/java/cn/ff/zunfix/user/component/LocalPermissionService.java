package cn.ff.zunfix.user.component;

import cn.ff.zunfix.common.security.service.PermissionService;
import cn.ff.zunfix.user.service.SysUserRoleResourceCacheService;
import cn.ff.zunfix.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * _
 *
 * @author fengfan 2020/10/12
 */
@Service
public class LocalPermissionService extends PermissionService {
    private SysUserRoleResourceCacheService sysUserRoleResourceCacheService;
    private SysUserService sysUserService;

    public LocalPermissionService(SysUserRoleResourceCacheService sysUserRoleResourceCacheService, SysUserService sysUserService) {
        this.sysUserRoleResourceCacheService = sysUserRoleResourceCacheService;
        this.sysUserService = sysUserService;

    }


    @Override
    protected Map<String, List<String>> getResourceRolesMap() {
        return sysUserRoleResourceCacheService.getResourceRolesMap();
    }

    @Override
    protected List<String> getUserHadRoles(String userId) {
        return sysUserService.getUserOwnRoleCodes(userId);
    }
}
