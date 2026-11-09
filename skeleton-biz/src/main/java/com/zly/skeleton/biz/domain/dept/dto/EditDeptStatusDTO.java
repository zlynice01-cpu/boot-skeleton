package com.zly.skeleton.biz.domain.dept.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 修改部门状态参数（表格内开关，仅提交 id + status）
 *
 * @author ZLY
 * @since 2026/11/9 14:00
 */
@Data
public class EditDeptStatusDTO
{
    @NotNull(message = "缺少部门id")
    private Long id;

    @NotEmpty(message = "缺少部门状态")
    private String status;

}
