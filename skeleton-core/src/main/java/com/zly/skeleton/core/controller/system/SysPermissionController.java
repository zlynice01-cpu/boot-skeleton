package com.zly.skeleton.core.controller.system;

import com.zly.skeleton.biz.domain.promission.dto.CreatePermissionDTO;
import com.zly.skeleton.biz.domain.promission.dto.DeletePermissionDTO;
import com.zly.skeleton.biz.domain.promission.dto.EditPermissionDTO;
import com.zly.skeleton.biz.domain.promission.dto.MenusTreeDTO;
import com.zly.skeleton.biz.domain.promission.vo.MenusVO;
import com.zly.skeleton.biz.domain.role.dto.EditRolePermissionDTO;
import com.zly.skeleton.biz.service.system.SysPermissionService;
import com.zly.skeleton.common.web.entity.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/30 16:02
 */
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class SysPermissionController
{

    private final SysPermissionService permissionService;

    @GetMapping("/tree")
    public Result<List<MenusVO>> tree(MenusTreeDTO dto) {
        return permissionService.getPermissionTree(dto);
    }

    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody CreatePermissionDTO dto) {
        permissionService.create(dto);
        return Result.success();
    }

    @PostMapping("/edit")
    public Result<Void> edit(@Valid @RequestBody EditPermissionDTO dto) {
        permissionService.edit(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody DeletePermissionDTO dto) {
        permissionService.removeById(dto.getId());
        return Result.success();
    }

}
