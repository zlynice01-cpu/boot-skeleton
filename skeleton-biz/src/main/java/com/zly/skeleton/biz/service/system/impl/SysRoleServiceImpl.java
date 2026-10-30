package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.role.vo.RoleListItemVO;
import com.zly.skeleton.biz.enums.StatusEnum;
import com.zly.skeleton.biz.mapper.SysRoleMapper;
import com.zly.skeleton.biz.service.system.SysRoleService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.SysRole;
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

    @Override
    public List<SysRole> getUserRoleList(Long userId)
    {
        List<SysRole> roles = roleMapper.selectRoleListByUserId(userId);
        if (ObjectUtils.isEmpty(roles)) {
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
}
