package com.zly.skeleton.common.security.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 登录
 * @author ZLY
 * @since 2026/10/24 19:57
 */
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider
{

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException
    {
        super.additionalAuthenticationChecks(userDetails, authentication);
    }

}
