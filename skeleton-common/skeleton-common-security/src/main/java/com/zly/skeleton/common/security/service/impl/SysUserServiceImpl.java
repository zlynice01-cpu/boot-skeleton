package com.zly.skeleton.common.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.common.security.domain.entity.SysUser;
import com.zly.skeleton.common.security.mapper.SysUserMapper;
import com.zly.skeleton.common.security.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:18
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService
{

    private final SysUserMapper userMapper;

    @Override
    public SysUser getByUsername(String username)
    {
        List<SysUser> sysUserList = userMapper.selectList(
                Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUsername, username)
        );
        if (ObjectUtils.isEmpty(sysUserList))
        {
            return null;
        }
        return sysUserList.getFirst();
    }
}
