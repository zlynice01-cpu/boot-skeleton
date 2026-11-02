package com.zly.skeleton.biz.domain.promission.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/11/2 18:31
 */
@Data
public class EditPermissionDTO
{
    @NotNull(message = "缺少菜单id")
    private Long id;

    // 父权限ID，0为顶级
    private Long parentId;

    // 权限名称
    private String permissionName;

    // 权限标识：user:list、user:add
    private String permissionKey;

    // 类型 C目录 M菜单 B按钮
    private String permissionType;

    // 前端路由地址
    private String path;

    // 前端组件路径
    private String component;

    // 菜单图标
    private String icon;

    // 排序
    private Integer sort;

    // 是否显示 0隐藏 1显示
    private String visible;

    // 状态 0禁用 1正常
    private Integer status;

}
