package com.zly.skeleton.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.entity.SysUser;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:18
 */
public interface SysUserService extends IService<SysUser>
{

    SysUser getByUsername(String username);

}
    