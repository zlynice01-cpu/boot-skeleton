package com.zly.skeleton.biz.domain.role.vo;

import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/10/26 17:36
 */
@Data
public class RoleProfileVO
{

    // 角色id
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
