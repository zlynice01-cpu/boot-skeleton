package com.zly.skeleton.common.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 *
 * @author ZLY
 * @since 2026/10/20 16:59
 */
public class MyBCryptPasswordEncoder implements PasswordEncoder
{
    @Override
    public String encode(CharSequence rawPassword)
    {
        return rawPassword + "{decrypt}";
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword)
    {
        if (!StringUtils.hasLength(rawPassword)) {
            return false;
        }
        String encrypt = encode(rawPassword);
        return encrypt.equals(encodedPassword);
    }
}
