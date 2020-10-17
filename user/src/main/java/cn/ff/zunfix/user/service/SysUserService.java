package cn.ff.zunfix.user.service;

import cn.ff.zunfix.common.core.entity.SysRole;
import cn.ff.zunfix.common.security.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户相关
 *
 * @author fengfan 2020/10/12
 */
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;


    /**
     * 获取一个用户所拥有的全部角色Code
     */
    public List<String> getUserOwnRoleCodes(String userId) {
        List<SysRole> roles = sysUserMapper.getOneUserAllRoles(userId);
        return Optional.ofNullable(roles).orElse(new ArrayList<>()).stream().filter(Objects::nonNull).map(SysRole::getCode).collect(Collectors.toList());
    }
}
