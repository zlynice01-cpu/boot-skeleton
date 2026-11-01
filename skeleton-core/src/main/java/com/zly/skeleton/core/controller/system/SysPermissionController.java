package com.zly.skeleton.core.controller.system;

import com.zly.skeleton.biz.domain.promission.vo.MenusVO;
import com.zly.skeleton.biz.service.system.SysPermissionService;
import com.zly.skeleton.common.web.entity.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<List<MenusVO>> tree() {
        return permissionService.getPermissionTree();
    }

}
