package com.zly.skeleton.biz.controller;

import com.zly.skeleton.biz.domain.dto.test.VerifyCodeDTO;
import com.zly.skeleton.common.security.entity.CaptchaInfo;
import com.zly.skeleton.common.security.filter.LoginFilter;
import com.zly.skeleton.common.util.VerifyCode;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;

/**
 *
 * @author ZLY
 * @since 2026/10/20 11:47
 */
@RestController
@RequestMapping("/test/login")
public class TestController
{

    @GetMapping("/test-01")
    public String test()
    {
        return "success";
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response)
    {
        VerifyCode verifyCode = new VerifyCode();
        BufferedImage image = verifyCode.getImage();
        String captcha = verifyCode.getText();
        CaptchaInfo captchaInfo = CaptchaInfo.instance(captcha);
        HttpSession session = request.getSession();
        session.setAttribute(LoginFilter.CAPTCHA_SESSION_KEY, captchaInfo);
        ResponseUtils.writeImage(response, image);
    }

    @PostMapping("/verCaptcha")
    public Result<Void> verCaptcha(@Valid @RequestBody VerifyCodeDTO dto, HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session == null)
        {
            return Result.error("验证码已失效");
        }
        CaptchaInfo captchaInfo = (CaptchaInfo) session.getAttribute(LoginFilter.CAPTCHA_SESSION_KEY);
        if (captchaInfo == null)
        {
            return Result.error("验证码已失效");
        }
        Boolean passed = captchaInfo.verifyCode(dto.getCode());
        if (Boolean.TRUE.equals(passed))
        {
            // verifyCode 已把验证码标记为已使用，这里放回去会失去一次性语义，
            // 因此校验接口只用于调试，登录时请重新获取验证码
            return Result.success();
        }
        return Result.error("验证码不正确");
    }

}
