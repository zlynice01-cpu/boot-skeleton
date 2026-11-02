package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.role.dto.CreateRoleDTO;
import com.zly.skeleton.biz.domain.role.dto.EditRolePermissionDTO;
import com.zly.skeleton.biz.domain.role.dto.EditRoleDTO;
import com.zly.skeleton.biz.domain.role.dto.RolePageDTO;
import com.zly.skeleton.biz.domain.role.vo.RoleListItemVO;
import com.zly.skeleton.biz.domain.role.vo.RolePageItemVO;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import jakarta.validation.Valid;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 16:36
 */
public interface SysRoleService extends IService<SysRole>
{
    List<SysRole> getUserRoleList(Long userId);

    Result<List<RoleListItemVO>> getAllRoleList();

    Result<PageResponse<RolePageItemVO>> getRolePage(@Valid RolePageDTO dto);

    void create(@Valid CreateRoleDTO dto);

    void postponeSort(Integer currentSort);

    void postponeSort(Integer currentSort, Long id);

    void postponeSort(Integer currentSort, Integer maxSort, Long id);

    void edit(EditRoleDTO dto);

    void assignMenu(@Valid EditRolePermissionDTO dto);
}
    