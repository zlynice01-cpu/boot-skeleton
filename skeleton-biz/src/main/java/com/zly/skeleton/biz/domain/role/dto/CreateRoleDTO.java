package com.zly.skeleton.biz.domain.role.dto;

import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/11/1 20:49
 */
@Data
public class CreateRoleDTO
{
    // 角色名称
    private String roleName;

    // 角色标识，如 admin、common
    private String roleKey;

    // 排序号
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;
}
