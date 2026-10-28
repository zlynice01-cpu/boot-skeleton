package com.zly.skeleton.common.security.handler;

import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

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
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
        log.info("[AuthSuccessHandler.onAuthenticationSuccess]:登录认证成功, user={}", authentication.getName());

        // 1. 写入当前请求的 Security 上下文
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // 2. 持久化到 Session，下一请求由 SecurityContextHolderFilter 自动还原
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);

        // 3. 返回登录结果
        ResponseUtils.writeJson(response, Result.success(authentication.getName(), "登录成功"));
    }

}
