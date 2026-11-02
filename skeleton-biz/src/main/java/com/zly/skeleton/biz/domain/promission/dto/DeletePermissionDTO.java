package com.zly.skeleton.biz.domain.promission.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/11/2 18:28
 */
@Data
public class DeletePermissionDTO
{
    @NotNull(message = "缺少菜单id")
    private Long id;

}
