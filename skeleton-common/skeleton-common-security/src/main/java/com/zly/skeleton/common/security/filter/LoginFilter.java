package com.zly.skeleton.common.security.filter;

import com.alibaba.fastjson2.JSON;
import com.zly.skeleton.common.security.entity.CaptchaInfo;
import com.zly.skeleton.common.security.entity.LoginParamDTO;
import com.zly.skeleton.common.web.constants.HttpConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 *
 * @author ZLY
 * @since 2026/10/23 18:01
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter
{
    /** 验证码在 Session 中的键名 */
    public static final String CAPTCHA_SESSION_KEY = "captcha";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        if (!HttpConstants.POST.equals(request.getMethod()))
        {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        // 验证码一次性使用：取出后立即从 Session 移除
        HttpSession session = request.getSession();
        CaptchaInfo captchaInfo = (CaptchaInfo) session.getAttribute(CAPTCHA_SESSION_KEY);
        session.removeAttribute(CAPTCHA_SESSION_KEY);

        String contentType = request.getContentType();
        boolean isJson = contentType != null
                && contentType.toLowerCase().startsWith(HttpConstants.CONTENT_TYPE_JSON);

        String username;
        String password;
        String code;

        if (isJson)
        {
            LoginParamDTO dto;
            try
            {
                dto = JSON.parseObject(request.getInputStream(), LoginParamDTO.class);
            }
            catch (IOException e)
            {
                throw new AuthenticationServiceException("请求体读取失败", e);
            }
            if (dto == null)
            {
                throw new AuthenticationServiceException("请求体格式错误");
            }
            username = dto.getUsername();
            password = dto.getPassword();
            code = dto.getCode();
        }
        else
        {
            // application/x-www-form-urlencoded
            username = request.getParameter("username");
            password = request.getParameter("password");
            code = request.getParameter("code");
        }

        checkCode(code, captchaInfo);

        username = username == null ? "" : username.trim();
        password = password == null ? "" : password;

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 校验验证码
     */
    private void checkCode(String code, CaptchaInfo captchaInfo)
    {
        if (code == null || captchaInfo == null || code.isBlank() || !captchaInfo.verifyCode(code))
        {
            // 验证码不正确
            throw new AuthenticationServiceException("验证码不正确");
        }
    }
}
