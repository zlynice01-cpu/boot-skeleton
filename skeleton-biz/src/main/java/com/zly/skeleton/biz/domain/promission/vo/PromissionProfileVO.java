package com.zly.skeleton.biz.domain.promission.vo;

import com.zly.skeleton.common.util.inter.CreateTree;
import lombok.Data;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 17:37
 */
@Data
public class PromissionProfileVO implements CreateTree<PromissionProfileVO>
{
    // 权限id
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

    // 子权限列表
    private List<PromissionProfileVO> children;

    @Override
    public String getCode()
    {
        return String.valueOf(this.id);
    }

    @Override
    public String getParentCode()
    {
        return String.valueOf(this.parentId);
    }

    @Override
    public void setChildren(List<PromissionProfileVO> childrenTree) {
        this.children = childrenTree;
    }


}
