package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.dept.vo.DeptProfileVO;
import com.zly.skeleton.biz.domain.role.vo.RoleProfileVO;
import com.zly.skeleton.biz.domain.user.dto.CreateUserDTO;
import com.zly.skeleton.biz.domain.user.dto.EditUserProfileDTO;
import com.zly.skeleton.biz.domain.user.dto.UserPageDTO;
import com.zly.skeleton.biz.domain.user.dto.UserProfileDTO;
import com.zly.skeleton.biz.domain.user.vo.LoginUserVO;
import com.zly.skeleton.biz.domain.user.vo.UserPageItemVO;
import com.zly.skeleton.biz.domain.user.vo.UserProfileVO;
import com.zly.skeleton.biz.mapper.SysDeptMapper;
import com.zly.skeleton.biz.mapper.SysPermissionMapper;
import com.zly.skeleton.biz.mapper.SysRoleMapper;
import com.zly.skeleton.biz.mapper.SysUserMapper;
import com.zly.skeleton.biz.service.system.SysUserRoleService;
import com.zly.skeleton.biz.service.system.SysUserService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.enums.BizErrorEnum;
import com.zly.skeleton.common.web.exception.BizException;
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
    private final SysUserRoleService userRoleService;
    private final SysPermissionMapper permissionMapper;
    private final SysDeptMapper deptMapper;

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
    public Result<Void> create(CreateUserDTO dto)
    {
        dto.initAndCheck();
        String username = dto.getUsername();
        SysUser dbUser = getByUsername(username);
        if (dbUser != null) {
            throw new BizException(BizErrorEnum.USERNAME_EXIST);
        }

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        save(sysUser);

        Long userId = sysUser.getId();
        List<Long> roleIds = dto.getRoleIds();
        if (ObjectUtils.isNotEmpty(roleIds)) {
            List<SysUserRole> userRoles = new ArrayList<>();
            for (Long roleId : roleIds)
            {
                if (roleId == null)
                {
                    continue;
                }
                SysUserRole userRole = new SysUserRole(userId, roleId);
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }

        return Result.success();
    }

    @Override
    public Result<UserProfileVO> getUserProfile(UserProfileDTO dto)
    {
        Result<LoginUserVO> userInfo = getLoginUser(dto);
        Boolean flag = userInfo.getFlag();
        if (Boolean.FALSE.equals(flag)) {
            return Result.error(userInfo.getCode(), userInfo.getMsg());
        }
        LoginUserVO data = userInfo.getData();
        if (data == null) {
            return Result.success(null);
        }
        UserProfileVO vo = new UserProfileVO();
        BeanUtils.copyProperties(data, vo);
        List<RoleProfileVO> roleList = data.getRoleList();
        if (ObjectUtils.isNotEmpty(roleList)) {
            Set<Long> roleIds = roleList.stream().filter(Objects::nonNull)
                    .map(RoleProfileVO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            vo.setRoleIds(roleIds);
        }
        DeptProfileVO deptInfo = data.getDeptInfo();
        if (deptInfo != null) {
            vo.setDeptId(deptInfo.getId());
        }

        return Result.success(vo);
    }

    @Override
    public Result<LoginUserVO> getLoginUser(UserProfileDTO dto)
    {
        String username = dto.getUsername();
        Long userId = dto.getUserId();
        SysUser user = null;
        if (StringUtils.isNotBlank(username)) {
            user = getByUsername(username);
        }
        if (user == null && userId != null) {
            user = getById(userId);
        }
        if (user == null) {
            throw new BizException(BizErrorEnum.USER_NOT_FOUND);
        }

        userId = userId == null ? user.getId() : userId;

        Long deptId = user.getDeptId();
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

        SysDept sysDept = null;
        if (deptId != null) {
            sysDept = deptMapper.selectById(deptId);
        }

        LoginUserVO vo = LoginUserVO.createVO(user, roles, permissions, sysDept);

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

    @Override
    public Result<Void> edit(EditUserProfileDTO dto)
    {
        SysUser userEntity = new SysUser();
        BeanUtils.copyProperties(dto, userEntity);
        // 更新用户信息
        updateById(userEntity);
        Long userId = userEntity.getId();
        List<Long> roleIds = dto.getRoleIds();
        // 更新角色关联信息
        userRoleService.updateUserRole(userId, roleIds);
        return Result.success();
    }
}
