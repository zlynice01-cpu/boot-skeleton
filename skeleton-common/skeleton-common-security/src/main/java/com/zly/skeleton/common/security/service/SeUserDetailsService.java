package com.zly.skeleton.common.security.service;

import com.zly.skeleton.mybatisplus.entity.SysPermission;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import com.zly.skeleton.mybatisplus.entity.SysUser;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ZLY
 * @since 2026/10/26 16:29
 */
public interface SeUserDetailsService
{

    /**
     * 获取security用户信息
     * @param username 用户名
     * @return 用户信息
     */
    SysUser getByUsername(String username);

    /**
     * 获取用户角色
     * @param userId 用户id
     * @return 角色列表
     */
    List<SysRole> getRoleList(Long userId);

    /**
     * 根据角色Id查权限
     * @param roleIdSet 角色id
     * @return 权限列表
     */
    List<SysPermission> getPermissionsByRoleIds(Collection<Long> roleIdSet);

}
    