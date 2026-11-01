package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.mapper.SysUserRoleMapper;
import com.zly.skeleton.biz.service.system.SysUserRoleService;
import com.zly.skeleton.mybatisplus.entity.SysUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author ZLY
 * @since 2026/10/30 13:51
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService
{
    private final SysUserRoleMapper userRoleMapper;

    @Override
    public void updateUserRole(Long userId, Collection<Long> roleIds)
    {
        Set<Long> dbRoleIds = getRoleIdsByUserId(userId);
        if (ObjectUtils.isEmpty(dbRoleIds) && ObjectUtils.isEmpty(roleIds)) {
            return;
        }
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId);
        userRoleMapper.delete(queryWrapper);
        if (ObjectUtils.isEmpty(roleIds)) {
            return;
        }
        List<SysUserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds)
        {
            SysUserRole userRole = new SysUserRole(userId, roleId);
            userRoles.add(userRole);
        }
        saveBatch(userRoles);
    }

    @Override
    public Set<Long> getRoleIdsByUserId(Long userId)
    {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class).eq(SysUserRole::getUserId, userId);
        List<SysUserRole> userRoles = userRoleMapper.selectList(queryWrapper);
        if (ObjectUtils.isEmpty(userRoles)) {
            return Set.of();
        }
        return userRoles.stream().filter(Objects::nonNull)
                .map(SysUserRole::getRoleId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }


}
