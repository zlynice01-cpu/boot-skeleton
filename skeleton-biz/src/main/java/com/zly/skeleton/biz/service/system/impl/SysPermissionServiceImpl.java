package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.promission.dto.CreatePermissionDTO;
import com.zly.skeleton.biz.domain.promission.dto.EditPermissionDTO;
import com.zly.skeleton.biz.domain.promission.dto.MenusTreeDTO;
import com.zly.skeleton.biz.domain.promission.vo.MenusVO;
import com.zly.skeleton.biz.mapper.SysPermissionMapper;
import com.zly.skeleton.biz.service.system.SysPermissionService;
import com.zly.skeleton.common.util.TreeUtil;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.SysPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author ZLY
 * @since 2026/10/26 17:04
 */
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService
{
    private final SysPermissionMapper permissionMapper;

    @Override
    public List<SysPermission> getPermissionsByRoleIds(Collection<Long> roleIdSet)
    {
        if (ObjectUtils.isEmpty(roleIdSet))
        {
            return List.of();
        }
        List<SysPermission> permissions = permissionMapper.selectPermissionsByRoleIds(roleIdSet);
        if (ObjectUtils.isEmpty(permissions))
        {
            return List.of();
        }
        return permissions;
    }

    @Override
    public Result<List<MenusVO>> getPermissionTree(MenusTreeDTO dto)
    {
        Long roleId = dto.getRoleId();
        List<SysPermission> menuList = null;
        if (roleId == null)
        {
            menuList = list(Wrappers.lambdaQuery(SysPermission.class).orderByAsc(SysPermission::getSort));
        } else {
            menuList = permissionMapper.selectPermissionsByRoleIds(List.of(roleId));
        }

        List<MenusVO> voList = menuList.stream().filter(Objects::nonNull)
                .map(item -> {
                    MenusVO menusVO = new MenusVO();
                    BeanUtils.copyProperties(item, menusVO);
                    return menusVO;
                }).toList();
        List<MenusVO> menusVOS = TreeUtil.buildTree(voList, "0");
        return Result.success(menusVOS);
    }

    @Override
    public void create(CreatePermissionDTO dto)
    {
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(dto, permission);
        save(permission);
    }

    @Override
    public void edit(EditPermissionDTO dto)
    {
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(dto, permission);
        updateById(permission);
    }

    @Override
    public Long getMenuCount()
    {
        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.lambdaQuery(SysPermission.class);
        return count(queryWrapper);
    }
}
