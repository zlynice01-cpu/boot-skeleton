package com.zly.skeleton.common.security.handler;

import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 *
 * @author ZLY
 * @since 2026/10/23 14:47
 */
@Slf4j
public class LogoutSuccessfulHandler implements LogoutSuccessHandler
{
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        log.info("[LogoutSuccessfulHandler.onLogoutSuccess]:退出登录成功");
        ResponseUtils.writeJson(response, Result.success());
    }
}
