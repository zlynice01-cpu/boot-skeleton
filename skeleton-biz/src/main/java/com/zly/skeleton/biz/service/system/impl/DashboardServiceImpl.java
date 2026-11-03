package com.zly.skeleton.biz.service.system.impl;

import com.zly.skeleton.biz.domain.dashboard.vo.DashboardInfoVO;
import com.zly.skeleton.biz.service.system.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author ZLY
 * @since 2026/11/3 14:57
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService
{
    private final SysUserService userService;
    private final SysRoleService roleService;
    private final SysPermissionService permissionService;
    private final SysConfigService configService;

    @Override
    public DashboardInfoVO summary()
    {
        Long userCount = userService.getUserCount();
        Long roleCount = roleService.getRoleCount();
        Long permCount = permissionService.getMenuCount();
        Long configCount = configService.getConfigCount();
        return new DashboardInfoVO(userCount, roleCount, permCount, configCount);
    }
}
