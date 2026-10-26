package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.mybatisplus.entity.SysPermission;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 17:04
 */
public interface SysPermissionService extends IService<SysPermission>
{

    List<SysPermission> getPermissionsByRoleIds(Collection<Long> roleIdSet);

}
    