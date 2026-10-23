package com.zly.skeleton.common.security.handler;

import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:53
 */
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler
{
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
    {
        log.info("[AuthFailureHandler.onAuthenticationFailure]:登录认证失败");
        ResponseUtils.writeJson(response, Result.error(exception.getMessage()));
    }
}
