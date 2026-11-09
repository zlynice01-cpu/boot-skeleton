package com.zly.skeleton.biz.domain.dept.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 编辑部门参数
 *
 * @author ZLY
 * @since 2026/11/9 10:00
 */
@Data
public class EditDeptDTO
{
    @NotNull(message = "缺少部门id")
    private Long id;

    // 父部门ID，0 表示顶级部门
    private Long parentId;

    @NotEmpty(message = "缺少部门名称")
    private String deptName;

    // 排序
    private Integer sort;

    // 负责人姓名
    private String leader;

    // 负责人用户ID
    private Long leaderUserId;

    // 联系电话
    private String phone;

    // 状态 0正常 1停用
    private String status;

}
