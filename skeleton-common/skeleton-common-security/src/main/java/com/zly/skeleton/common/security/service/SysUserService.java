package com.zly.skeleton.common.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import com.zly.skeleton.common.security.entity.user.dto.SeCreateUserDTO;
import com.zly.skeleton.common.web.entity.Result;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:18
 */
public interface SysUserService extends IService<SysUser>
{

    SysUser getByUsername(String username);

    Result<Void> create(SeCreateUserDTO dto);
}
    