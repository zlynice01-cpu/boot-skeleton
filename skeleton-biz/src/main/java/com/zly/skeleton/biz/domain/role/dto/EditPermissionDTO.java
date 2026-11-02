package com.zly.skeleton.biz.domain.role.dto;

import lombok.Data;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/11/2 16:04
 */
@Data
public class EditPermissionDTO
{

    private Long roleId;

    private List<Long> permissionIds;

}
