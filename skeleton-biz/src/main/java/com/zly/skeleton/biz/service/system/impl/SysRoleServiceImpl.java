package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.mapper.SysRoleMapper;
import com.zly.skeleton.biz.service.system.SysRoleService;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 16:37
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService
{
    private final SysRoleMapper roleMapper;

    @Override
    public List<SysRole> getUserRoleList(Long userId)
    {
        List<SysRole> roles = roleMapper.selectRoleListByUserId(userId);
        if (ObjectUtils.isEmpty(roles)) {
            return List.of();
        }
        return roles;
    }
}
