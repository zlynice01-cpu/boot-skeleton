package com.zly.skeleton.biz.domain.config.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 删除配置参数
 *
 * @author ZLY
 * @since 2026/11/2 10:20
 */
@Data
public class DeleteConfigDTO
{
    @NotNull(message = "缺少配置id")
    private Long id;

}
