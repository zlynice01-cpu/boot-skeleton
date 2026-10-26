package com.zly.skeleton.common.security.util;

import cn.hutool.core.lang.Assert;
import com.zly.skeleton.common.security.entity.LoginUser;
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

    /**
     * 获取登录用户主体，未登录或匿名访问返回 null
     */
    public static LoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 匿名访问时 principal 为字符串 "anonymousUser"，必须用 instanceof 判断
        if (authentication == null || !(authentication.getPrincipal() instanceof LoginUser loginUser)) {
            return null;
        }
        return loginUser;
    }

    /**
     * 当前登录用户ID，未登录返回 null。
     * 供自动填充等场景使用，不抛异常以免污染登录等未认证流程
     */
    public static Long getUserId() {
        LoginUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUserId();
    }

    /**
     * 当前登录用户名，未登录返回 null
     */
    public static String getUsername() {
        LoginUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUsername();
    }

    /**
     * 当前登录用户所属部门ID，未登录返回 null
     */
    public static Long getDeptId() {
        LoginUser loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getDeptId();
    }

    public static String getIp() {
        Authentication authentication = getAuthentication();
        if (!(authentication.getDetails() instanceof WebAuthenticationDetails details)) {
            return "";
        }
        return details.getRemoteAddress();
    }

}
