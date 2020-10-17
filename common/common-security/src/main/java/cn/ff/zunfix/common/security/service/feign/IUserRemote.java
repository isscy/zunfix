package cn.ff.zunfix.common.security.service.feign;

import cn.ff.zunfix.common.core.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * _
 *
 * @author fengfan 2020/9/14
 */
@FeignClient(value = "user")
public interface IUserRemote {


    /**
     * 获取全部资源对应的角色 ： 访问资源所需的角色们
     */
    @GetMapping("sysResource/cache/resourceRolesRel")
     R<Map<String, List<String>>> getResourceRolesRel();

    /**
     * 获取一个用户所拥有的全部角色
     */
    @GetMapping("sysUser/userOwnRoleCodes/{userId}")
    public R<List<String>> getUserOwnRoleCodes(@PathVariable String userId);

}
