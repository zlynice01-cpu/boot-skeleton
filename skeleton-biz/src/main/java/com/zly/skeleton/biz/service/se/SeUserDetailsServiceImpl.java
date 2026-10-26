package com.zly.skeleton.biz.service.se;

import com.zly.skeleton.biz.service.system.SysPermissionService;
import com.zly.skeleton.biz.service.system.SysRoleService;
import com.zly.skeleton.biz.service.system.SysUserService;
import com.zly.skeleton.common.security.service.SeUserDetailsService;
import com.zly.skeleton.common.web.enums.BizErrorEnum;
import com.zly.skeleton.common.web.exception.BizException;
import com.zly.skeleton.mybatisplus.entity.SysPermission;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 16:31
 */
@Service
@RequiredArgsConstructor
public class SeUserDetailsServiceImpl implements SeUserDetailsService
{
    private final SysUserService sysUserService;
    private final SysRoleService roleService;
    private final SysPermissionService permissionService;

    @Override
    public SysUser getByUsername(String username)
    {
        return sysUserService.getByUsername(username);
    }

    @Override
    public List<SysRole> getRoleList(Long userId)
    {
        return roleService.getUserRoleList(userId);
    }

    @Override
    public List<SysPermission> getPermissionsByRoleIds(Collection<Long> roleIdSet)
    {
        return permissionService.getPermissionsByRoleIds(roleIdSet);
    }

}
