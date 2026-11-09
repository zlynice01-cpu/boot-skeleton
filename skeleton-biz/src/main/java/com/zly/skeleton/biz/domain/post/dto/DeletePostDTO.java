package com.zly.skeleton.biz.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 删除岗位参数
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
@Data
public class DeletePostDTO
{
    @NotNull(message = "缺少岗位id")
    private Long id;

}
