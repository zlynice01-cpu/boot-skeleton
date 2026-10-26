package com.zly.skeleton.common.security.service.impl;

import com.zly.skeleton.common.security.entity.LoginUser;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import com.zly.skeleton.common.security.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return new LoginUser(user, List.of(new SimpleGrantedAuthority("user")));
    }
}
