package com.zly.skeleton.biz.domain.dto.test;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/10/23 17:27
 */
@Data
public class VerifyCodeDTO
{
    @NotEmpty(message = "缺少验证码")
    private String code;
}
