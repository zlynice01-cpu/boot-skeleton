package com.zly.skeleton.common.security.util;

import cn.hutool.core.lang.Assert;
import com.zly.skeleton.common.web.exception.BizException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import static com.zly.skeleton.common.web.enums.BaseErrorEnum.LOGIN_AGIN;

/**
 *
 * @author ZLY
 * @since 2026/10/24 20:33
 */
public class UserContextUtil
{
    public static Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication, () -> {
            throw new BizException(LOGIN_AGIN);
        });
        return authentication;
    }

    public static String getIp() {
        Authentication authentication = getAuthentication();
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        return details == null ? "" : details.getRemoteAddress();
    }

}
