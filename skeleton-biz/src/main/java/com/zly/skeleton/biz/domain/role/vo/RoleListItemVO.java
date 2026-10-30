package com.zly.skeleton.biz.domain.role.vo;

import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/10/30 9:58
 */
@Data
public class RoleListItemVO
{
    private Long id;

    // 角色名称
    private String roleName;

    // 角色标识，如 admin、common
    private String roleKey;

    // 排序号
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;
}
