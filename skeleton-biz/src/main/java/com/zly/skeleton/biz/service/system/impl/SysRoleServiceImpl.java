package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.role.dto.CreateRoleDTO;
import com.zly.skeleton.biz.domain.role.dto.EditRolePermissionDTO;
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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
        postponeSort(currentSort, null);
    }

    @Override
    public void postponeSort(Integer currentSort, Long id)
    {
        postponeSort(currentSort, 9999, id);
    }

    @Override
    public void postponeSort(Integer currentSort, Integer maxSort, Long id)
    {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getSort, currentSort)
                .eq(SysRole::getStatus, StatusEnum.YES.getCode());
        List<SysRole> roles = roleMapper.selectList(queryWrapper);
        if (ObjectUtils.isEmpty(roles))
        {
            return;
        }
        roleMapper.postponeSort(currentSort, maxSort, id);
    }

    @Override
    public void edit(EditRoleDTO dto)
    {
        Integer sort = dto.getSort();
        postponeSort(sort, dto.getId());
        SysRole role = new SysRole();
        BeanUtils.copyProperties(dto, role);
        updateById(role);
    }

    @Override
    public void assignMenu(EditRolePermissionDTO dto)
    {
        Long roleId = dto.getRoleId();
        if (roleId == null)
        {
            return;
        }
        List<SysRolePermission> rolePermissionList = rolePermissionMapper
                .selectList(Wrappers.lambdaQuery(SysRolePermission.class).eq(SysRolePermission::getRoleId, roleId));

        // 数据库存储权限id
        Set<Long> dbPermIds = rolePermissionList.stream().filter(Objects::nonNull)
                .map(SysRolePermission::getPermissionId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 需要修改的权限id
        Set<Long> permIds = dto.getPermissionIds().stream().filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (ObjectUtils.isEmpty(dbPermIds) && ObjectUtils.isEmpty(permIds)) {
            return;
        }

        Set<Long> addPermIds = new HashSet<>();
        Set<Long> removePermIds = new HashSet<>();

        if (ObjectUtils.isEmpty(dbPermIds)) {
            addPermIds = permIds;
        } else {
            addPermIds = permIds.stream().filter(item -> !dbPermIds.contains(item)).collect(Collectors.toSet());
        }

        if (ObjectUtils.isEmpty(permIds)) {
            removePermIds = dbPermIds;
        } else {
            removePermIds = dbPermIds.stream().filter(item -> !permIds.contains(item)).collect(Collectors.toSet());
        }

        if (ObjectUtils.isNotEmpty(removePermIds))
        {
            rolePermissionMapper.delete(Wrappers.lambdaQuery(SysRolePermission.class)
                    .eq(SysRolePermission::getRoleId, roleId)
                    .in(SysRolePermission::getPermissionId, removePermIds));
        }
        if (ObjectUtils.isNotEmpty(addPermIds)) {
            for (Long permissionId : addPermIds)
            {
                SysRolePermission rolePermission = new SysRolePermission(roleId, permissionId);
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }

    @Override
    public Long getRoleCount()
    {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.lambdaQuery(SysRole.class);
        return count(queryWrapper);
    }

}
