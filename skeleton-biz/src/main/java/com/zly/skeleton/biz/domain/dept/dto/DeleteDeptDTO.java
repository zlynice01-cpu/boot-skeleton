package com.zly.skeleton.biz.domain.dept.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 删除部门参数
 *
 * @author ZLY
 * @since 2026/11/9 10:00
 */
@Data
public class DeleteDeptDTO
{
    @NotNull(message = "缺少部门id")
    private Long id;

}
