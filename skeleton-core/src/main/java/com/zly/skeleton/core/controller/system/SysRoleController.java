package com.zly.skeleton.core.controller.system;

import com.zly.skeleton.biz.domain.role.dto.RoleListDTO;
import com.zly.skeleton.biz.domain.role.vo.RoleListItemVO;
import com.zly.skeleton.biz.service.system.SysRoleService;
import com.zly.skeleton.common.web.entity.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/30 9:55
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController
{
    private final SysRoleService roleService;

    @GetMapping("/list")
    public Result<List<RoleListItemVO>> list(@Valid RoleListDTO dto) {
        return roleService.getAllRoleList();
    }

}
