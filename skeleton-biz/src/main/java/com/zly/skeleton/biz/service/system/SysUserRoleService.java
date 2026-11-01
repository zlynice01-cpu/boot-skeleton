package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.mybatisplus.entity.SysUserRole;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ZLY
 * @since 2026/10/30 13:50
 */
public interface SysUserRoleService extends IService<SysUserRole>
{

    void updateUserRole(Long userId, Collection<Long> roleIds);

    Set<Long> getRoleIdsByUserId(Long userId);

}
    