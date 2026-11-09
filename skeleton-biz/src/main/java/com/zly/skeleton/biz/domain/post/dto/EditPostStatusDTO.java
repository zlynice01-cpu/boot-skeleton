package com.zly.skeleton.biz.domain.post.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 修改岗位状态参数（表格内开关，仅提交 id + status）
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
@Data
public class EditPostStatusDTO
{
    @NotNull(message = "缺少岗位id")
    private Long id;

    @NotEmpty(message = "缺少岗位状态")
    private String status;

}
