package cn.ff.zunfix.auth.service;

import cn.ff.zunfix.common.security.mapper.SysUserMapper;
import cn.ff.zunfix.common.core.entity.SysRole;
import cn.ff.zunfix.common.core.entity.SysUser;
import cn.ff.zunfix.common.security.constant.SecurityConstant;
import cn.ff.zunfix.common.security.entity.BaseUserDetail;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * _
 *
 * @author fengfan 2020/8/11
 */
@Service("userDetailsService")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* *
         * TODO 待处理
         */
        SysUser user = sysUserMapper.getByUserName(username, true);
        if (user == null) {
            throw new UsernameNotFoundException("用户[ " + username + " ] 未注册或被禁用");
        }
        return buildUserInfoDetail(user);
    }

    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.getByPhone(phone, true);
        if (user == null) {
            throw new UsernameNotFoundException("手机号[ " + phone + " ] 未注册或被禁用");
        }
        return buildUserInfoDetail(user);
    }

    private BaseUserDetail buildUserInfoDetail(SysUser sysUser) {
        List<SysRole> roles = Optional.ofNullable(sysUserMapper.getOneUserAllRoles(sysUser.getId()))
                .orElse(new ArrayList<>());
        sysUser.setOfRoles(roles);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(SecurityConstant.ROLE_FULLY));
        roles.stream().filter(Objects::nonNull).forEach(e -> { // 存储用户、角色信息到GrantedAuthority，并放到GrantedAuthority列表
            GrantedAuthority authority = new SimpleGrantedAuthority(e.getCode());
            authorities.add(authority);
        });
        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                sysUser.getUserName(),
                sysUser.getPassword(), "1".equals(sysUser.getStatus()), true, true, true,
                authorities);
        //cache.put(username, baseUserDetail);
        return new BaseUserDetail(sysUser, user);
    }

}
