package com.zly.skeleton.common.security.service.impl;

import com.zly.skeleton.common.security.entity.LoginUser;
import com.zly.skeleton.common.security.service.SeUserDetailsService;
import com.zly.skeleton.mybatisplus.entity.SysPermission;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:22
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService
{

    private final SeUserDetailsService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.getByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SysRole> roleList = userService.getRoleList(user.getId());
        Set<Long> roleIdSet = roleList.stream().filter(Objects::nonNull)
                .map(SysRole::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<SysPermission> permissions = userService.getPermissionsByRoleIds(roleIdSet);

        Set<SimpleGrantedAuthority> authorityList = permissions.stream().filter(Objects::nonNull)
                .map(item -> new SimpleGrantedAuthority(item.getPermissionKey()))
                .collect(Collectors.toSet());

        return new LoginUser(user, authorityList);
    }
}
