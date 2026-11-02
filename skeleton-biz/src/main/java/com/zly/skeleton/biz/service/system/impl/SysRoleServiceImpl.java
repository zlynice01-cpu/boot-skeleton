package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.role.dto.CreateRoleDTO;
import com.zly.skeleton.biz.domain.role.dto.EditPermissionDTO;
import com.zly.skeleton.biz.domain.role.dto.EditRoleDTO;
import com.zly.skeleton.biz.domain.role.dto.RolePageDTO;
import com.zly.skeleton.biz.domain.role.vo.RoleListItemVO;
import com.zly.skeleton.biz.domain.role.vo.RolePageItemVO;
import com.zly.skeleton.biz.enums.StatusEnum;
import com.zly.skeleton.biz.mapper.SysRoleMapper;
import com.zly.skeleton.biz.mapper.SysRolePermissionMapper;
import com.zly.skeleton.biz.service.system.SysRoleService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import com.zly.skeleton.mybatisplus.entity.SysRolePermission;
import com.zly.skeleton.mybatisplus.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author ZLY
 * @since 2026/10/26 16:37
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService
{
    private final SysRoleMapper roleMapper;
    private final SysRolePermissionMapper rolePermissionMapper;

    @Override
    public List<SysRole> getUserRoleList(Long userId)
    {
        List<SysRole> roles = roleMapper.selectRoleListByUserId(userId);
        if (ObjectUtils.isEmpty(roles))
        {
            return List.of();
        }
        return roles;
    }

    @Override
    public Result<List<RoleListItemVO>> getAllRoleList()
    {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class)
                .eq(SysRole::getStatus, StatusEnum.YES.getCode());
        List<SysRole> roles = roleMapper.selectList(queryWrapper);
        List<RoleListItemVO> voList = roles.stream().filter(Objects::nonNull)
                .map(item -> {
                    RoleListItemVO vo = new RoleListItemVO();
                    BeanUtils.copyProperties(item, vo);
                    return vo;
                }).toList();
        return Result.success(voList);
    }

    @Override
    public Result<PageResponse<RolePageItemVO>> getRolePage(RolePageDTO dto)
    {
        Integer status = dto.getStatus();
        IPage<SysRole> page = PageUtil.createPage(dto);
        IPage<RolePageItemVO> voPage = roleMapper
                .selectPage(page, Wrappers.lambdaQuery(SysRole.class)
                        .eq(status != null, SysRole::getStatus, status)
                        .orderByAsc(SysRole::getSort))
                .convert(RolePageItemVO::entityToVO);

        PageResponse<RolePageItemVO> voResult = PageUtil.getPageResult(voPage);

        return Result.success(voResult);
    }

    @Override
    public void create(CreateRoleDTO dto)
    {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(dto, sysRole);
        postponeSort(dto.getSort());
        save(sysRole);
    }

    @Override
    public void postponeSort(Integer currentSort)
    {
        postponeSort(currentSort, 9999);
    }

    @Override
    public void postponeSort(Integer currentSort, Integer maxSort)
    {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getSort, currentSort)
                .eq(SysRole::getStatus, StatusEnum.YES.getCode());
        List<SysRole> roles = roleMapper.selectList(queryWrapper);
        if (ObjectUtils.isEmpty(roles))
        {
            return;
        }
        roleMapper.postponeSort(currentSort, maxSort);
    }

    @Override
    public void edit(EditRoleDTO dto)
    {
        Integer sort = dto.getSort();
        postponeSort(sort);
        SysRole role = new SysRole();
        BeanUtils.copyProperties(dto, role);
        updateById(role);
    }

    @Override
    public void assignMenu(EditPermissionDTO dto)
    {
        Long roleId = dto.getRoleId();
        if (roleId == null)
        {
            return;
        }
        List<SysRolePermission> rolePermissionList = rolePermissionMapper
                .selectList(Wrappers.lambdaQuery(SysRolePermission.class).eq(SysRolePermission::getRoleId, roleId));
        if (ObjectUtils.isNotEmpty(rolePermissionList))
        {
            rolePermissionMapper.delete(Wrappers.lambdaQuery(SysRolePermission.class).eq(SysRolePermission::getRoleId, roleId));
        }
        List<Long> permissionIds = dto.getPermissionIds();
        if (ObjectUtils.isEmpty(permissionIds))
        {
            return;
        }
        for (Long permissionId : permissionIds)
        {
            SysRolePermission rolePermission = new SysRolePermission(roleId, permissionId);
            rolePermissionMapper.insert(rolePermission);
        }
    }

}
