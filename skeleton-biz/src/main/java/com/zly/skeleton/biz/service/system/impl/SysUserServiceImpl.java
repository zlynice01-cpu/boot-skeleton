package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.user.dto.UserPageDTO;
import com.zly.skeleton.biz.domain.user.dto.UserProfileDTO;
import com.zly.skeleton.biz.domain.user.vo.UserPageItemVO;
import com.zly.skeleton.biz.domain.user.vo.UserProfileVO;
import com.zly.skeleton.biz.mapper.SysDeptMapper;
import com.zly.skeleton.biz.mapper.SysPermissionMapper;
import com.zly.skeleton.biz.mapper.SysRoleMapper;
import com.zly.skeleton.biz.mapper.SysUserMapper;
import com.zly.skeleton.biz.service.system.SysUserService;
import com.zly.skeleton.common.security.entity.user.dto.SeCreateUserDTO;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.*;
import com.zly.skeleton.mybatisplus.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private final SysRoleMapper roleMapper;
    private final SysDeptMapper deptMapper;
    private final SysPermissionMapper permissionMapper;

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

    @Override
    public Result<UserProfileVO> getUserProfile(UserProfileDTO dto)
    {
        Long userId = dto.getUserId();
        SysUser userEntity = getById(userId);
        List<SysRole> roles = roleMapper.selectRoleListByUserId(userId);
        List<SysPermission> permissions = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(roles))
        {
            Set<Long> roleIdSet = roles.stream().filter(Objects::nonNull)
                    .map(SysRole::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            permissions = permissionMapper.selectPermissionsByRoleIds(roleIdSet);
        }

        UserProfileVO vo = UserProfileVO.createVO(userEntity, roles, permissions);

        return Result.success(vo);
    }

    @Override
    public Result<PageResponse<UserPageItemVO>> getPage(UserPageDTO dto)
    {
        IPage<UserPageItemVO> page = PageUtil.createPage(dto);
        IPage<UserPageItemVO> sysUserIPage = userMapper.selectUserPage(page, dto);
        PageResponse<UserPageItemVO> pageResult = PageUtil.getPageResult(sysUserIPage);

        return Result.success(pageResult);
    }
}
