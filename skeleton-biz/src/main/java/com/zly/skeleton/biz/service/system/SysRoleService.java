package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.mybatisplus.entity.SysRole;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 16:36
 */
public interface SysRoleService extends IService<SysRole>
{

    List<SysRole> getUserRoleList(Long userId);

}
    