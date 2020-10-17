package cn.ff.zunfix.user.controller;

import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.utils.Rs;
import cn.ff.zunfix.user.service.SysUserRoleResourceCacheService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * _
 *
 * @author fengfan 2020/9/14
 */
@RestController
@RequestMapping("sysResource")
@AllArgsConstructor
public class SysResourceController {

    private SysUserRoleResourceCacheService sysUserRoleResourceCacheService;





    /**
     * 获取resource-roles-rel 资源对应的角色们
     */
    @GetMapping("cache/resourceRolesRel")
    public R<Map<String, List<String>>> resourceRolesRel() {
        Map<String, List<String>> map = sysUserRoleResourceCacheService.getResourceRolesMap();
        return Rs.ok(map);
    }
}
