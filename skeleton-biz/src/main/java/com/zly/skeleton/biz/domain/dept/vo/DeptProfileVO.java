package com.zly.skeleton.biz.domain.dept.vo;

import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/10/26 17:33
 */
@Data
public class DeptProfileVO
{

    // 部门id
    private Long id;

    // 父部门ID
    private Long parentId;

    // 部门名称
    private String deptName;

    // 排序
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;

}
