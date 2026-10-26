package com.zly.skeleton.common.security.service.impl;

import com.zly.skeleton.common.security.domain.entity.SysUser;
import com.zly.skeleton.common.security.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:22
 */
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService
{

    private final SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.getByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!Integer.valueOf(1).equals(user.getEnabled()))
                .authorities("user")
                .build();
    }
}
