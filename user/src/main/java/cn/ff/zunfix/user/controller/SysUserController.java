package cn.ff.zunfix.user.controller;

import cn.ff.zunfix.common.core.entity.R;
import cn.ff.zunfix.common.core.entity.SysRole;
import cn.ff.zunfix.common.core.utils.Rs;
import cn.ff.zunfix.user.service.SysUserService;
import org.redisson.api.RList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * _
 *
 * @author fengfan 2020/9/14
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;







    /**
     * 获取一个用户所拥有的全部角色Code
     */
    @GetMapping("userOwnRoleCodes/{userId}")
    public R<List<String>> getUserOwnRoleCodes(@PathVariable String userId) {
        return Rs.ok(sysUserService.getUserOwnRoleCodes(userId));
    }
}
