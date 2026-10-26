package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.mapper.SysUserMapper;
import com.zly.skeleton.biz.service.system.SysUserService;
import com.zly.skeleton.common.security.entity.user.dto.SeCreateUserDTO;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

    @Override
    public Result<Void> create(SeCreateUserDTO dto)
    {
        dto.initAndCheck();
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        save(sysUser);
        return Result.success();
    }
}
