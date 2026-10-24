package com.zly.skeleton.common.security.handler;

import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.enums.BaseErrorEnum;
import com.zly.skeleton.common.web.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 *
 * @author ZLY
 * @since 2026/10/23 14:30
 */
@Slf4j
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint
{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException
    {
        log.info("[AuthenticationEntryPointHandler.commence]:登录状态校验失败");
        ResponseUtils.writeJson(response, Result.error(BaseErrorEnum.UNLOGIN));
    }
}
