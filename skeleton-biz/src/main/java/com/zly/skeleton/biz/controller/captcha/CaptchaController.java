package com.zly.skeleton.biz.controller.captcha;

import com.zly.skeleton.common.security.entity.CaptchaInfo;
import com.zly.skeleton.common.security.provider.MyAuthenticationProvider;
import com.zly.skeleton.common.util.VerifyCode;
import com.zly.skeleton.common.web.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

/**
 *
 * @author ZLY
 * @since 2026/10/24 17:51
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController
{

    @GetMapping("/create")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response)
    {
        VerifyCode verifyCode = new VerifyCode();
        BufferedImage image = verifyCode.getImage();
        String captcha = verifyCode.getText();
        CaptchaInfo captchaInfo = CaptchaInfo.instance(captcha);
        HttpSession session = request.getSession();
        session.setAttribute(MyAuthenticationProvider.CAPTCHA_SESSION_KEY, captchaInfo);
        ResponseUtils.writeImage(response, image);
    }

}
