package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.mapper.SysPermissionMapper;
import com.zly.skeleton.biz.service.system.SysPermissionService;
import com.zly.skeleton.mybatisplus.entity.SysPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
}
