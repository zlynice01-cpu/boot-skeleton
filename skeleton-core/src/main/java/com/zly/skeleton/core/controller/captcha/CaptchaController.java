package com.zly.skeleton.core.controller.captcha;

import com.zly.skeleton.biz.domain.config.dto.SysConfigDTO;
import com.zly.skeleton.biz.service.system.SysConfigService;
import com.zly.skeleton.cache.util.RedisCache;
import com.zly.skeleton.common.security.entity.CaptchaInfo;
import com.zly.skeleton.common.security.filter.LoginFilter;
import com.zly.skeleton.common.util.CaptchaCodeUtil;
import com.zly.skeleton.common.util.constant.RedisPrefixConstant;
import com.zly.skeleton.common.util.constant.SysConfigKeyConstant;
import com.zly.skeleton.common.web.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

/**
 *
 * @author ZLY
 * @since 2026/10/24 17:51
 */
@Slf4j
@RestController
@RequestMapping("/captcha")
@RequiredArgsConstructor
public class CaptchaController
{
    private final SysConfigService configService;

    @GetMapping("/create")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response)
    {
        SysConfigDTO config = configService.getConfigByCode(SysConfigKeyConstant.SYS_CAPTCHA_TIME);
        int effectTime = 300;
        try {
            effectTime = Integer.parseInt(config.getConfigValue());
        } catch (Exception e) {
            log.error("[CaptchaController.getCaptcha]", e);
        }
        CaptchaCodeUtil captchaCodeUtil = new CaptchaCodeUtil();
        BufferedImage image = captchaCodeUtil.getImage();
        String captcha = captchaCodeUtil.getText();

        CaptchaInfo captchaInfo = CaptchaInfo.instance(captcha, effectTime);
        HttpSession session = request.getSession();
        session.setAttribute(LoginFilter.CAPTCHA_SESSION_KEY, captchaInfo);
        ResponseUtils.writeImage(response, image);
    }

}
