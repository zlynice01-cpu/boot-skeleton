package com.zly.skeleton.common.security.config;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

/**
 *
 * @author ZLY
 * @since 2026/10/20 16:59
 */
public class MyBCryptPasswordEncoder implements PasswordEncoder
{

    private static final String SALT = "ENCODER";

    @Override
    public String encode(CharSequence rawPassword)
    {
        rawPassword = rawPassword + SALT;
        String hash = DigestUtil.md5Hex(rawPassword.toString());
        return DigestUtil.md5Hex(hash);
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
