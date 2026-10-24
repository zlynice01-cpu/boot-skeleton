package com.zly.skeleton.common.security.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author ZLY
 * @since 2026/10/24 11:35
 */
@Data
public class CaptchaInfo
{

    // 验证码
    private String code;

    // 有效时间 （秒）
    private Integer effectTime;

    // 失效时间
    private LocalDateTime expireTime;

    // 是否已使用
    private Boolean userStatus = false;

    private CaptchaInfo() {}

    /**
     * 创建实例 (验证码有效时间默认五分钟)
     * @param code 验证码
     * @return 实例
     */
    public static CaptchaInfo instance(String code) {
        return instance(code, 60 * 5);
    }

    /**
     * 创建实例 (验证码有效时间默认五分钟)
     * @param code 验证码
     * @param effectTime 有效时间
     * @return 实例
     */
    public static CaptchaInfo instance(@NotEmpty String code, Integer effectTime) {
        CaptchaInfo captchaInfo = new CaptchaInfo();
        captchaInfo.setCode(code);
        captchaInfo.setEffectTime(effectTime);
        return captchaInfo;
    }

    /**
     * 设置有效时间
     * @param effectTime 有效时间
     */
    public void setEffectTime(Integer effectTime)
    {
        this.effectTime = effectTime;
        this.expireTime = LocalDateTime.now().plusSeconds(effectTime);
    }

    /**
     * 获取剩余有效时间（毫秒）
     */
    public Long getResidualTime()
    {
        if (this.expireTime == null)
        {
            return 0L;
        }
        long millis = Duration.between(LocalDateTime.now(), this.expireTime).toMillis();
        return Math.max(millis, 0L);
    }

    /**
     * 是否已过期
     */
    public boolean isExpired()
    {
        return this.expireTime == null || LocalDateTime.now().isAfter(this.expireTime);
    }

    /**
     * 校验验证码（校验通过后才标记为已使用，校验失败不消费）
     *
     * @param code 用户输入的验证码
     * @return 是否校验通过
     */
    public Boolean verifyCode(String code)
    {
        if (Boolean.TRUE.equals(this.userStatus) || isExpired())
        {
            return false;
        }
        if (code == null || !this.code.equalsIgnoreCase(code))
        {
            return false;
        }
        this.userStatus = true;
        return true;
    }

    private String getCode()
    {
        return code;
    }

    private void setCode(String code)
    {
        this.code = code;
    }
}
