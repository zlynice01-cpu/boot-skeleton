package com.zly.skeleton.common.security.provider;

import com.alibaba.fastjson2.JSON;
import com.zly.skeleton.common.security.entity.CaptchaInfo;
import com.zly.skeleton.common.security.entity.LoginParamDTO;
import com.zly.skeleton.common.web.constants.HttpConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

/**
 * 登录
 * @author ZLY
 * @since 2026/10/24 19:57
 */
@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider
{

    /** 验证码在 Session 中的键名 */
    public static final String CAPTCHA_SESSION_KEY = "captcha";

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 验证码一次性使用：取出后立即从 Session 移除
        HttpSession session = request.getSession();
        CaptchaInfo captchaInfo = (CaptchaInfo) session.getAttribute(CAPTCHA_SESSION_KEY);
        session.removeAttribute(CAPTCHA_SESSION_KEY);

        String contentType = request.getContentType();
        boolean isJson = contentType != null && contentType.toLowerCase().startsWith(HttpConstants.CONTENT_TYPE_JSON);

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
            code = dto.getCode();
        }
        else
        {
            // application/x-www-form-urlencoded
            code = request.getParameter("code");
        }

        checkCode(code, captchaInfo);

        super.additionalAuthenticationChecks(userDetails, authentication);
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
