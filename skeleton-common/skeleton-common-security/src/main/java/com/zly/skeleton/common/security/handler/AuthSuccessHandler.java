package com.zly.skeleton.common.security.handler;

import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:48
 */
@Slf4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler
{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        log.info("[AuthSuccessHandler.onAuthenticationSuccess]:登录认证成功");
        ResponseUtils.writeJson(response, Result.success());
    }

}
