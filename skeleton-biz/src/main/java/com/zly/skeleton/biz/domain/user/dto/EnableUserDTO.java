package com.zly.skeleton.biz.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/11/9 11:37
 */
@Data
public class EnableUserDTO
{
    @NotNull(message = "缺少用户id")
    private Long id;
}
