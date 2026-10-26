package com.zly.skeleton.biz.controller.system;

import com.zly.skeleton.biz.domain.dept.dto.CheckDeptDTO;
import com.zly.skeleton.biz.domain.user.dto.CreateUserDTO;
import com.zly.skeleton.biz.service.system.SysDeptService;
import com.zly.skeleton.common.security.entity.user.dto.SeCreateUserDTO;
import com.zly.skeleton.biz.service.system.SysUserService;
import com.zly.skeleton.common.web.entity.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ZLY
 * @since 2026/10/26 9:35
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SysUserController
{
    private final SysUserService userService;

    private final SysDeptService deptService;

    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody CreateUserDTO dto) {
        Long deptId = dto.getDeptId();
        CheckDeptDTO checkDeptDTO = new CheckDeptDTO();
        checkDeptDTO.setDeptId(deptId);
        checkDeptDTO.setCheckStatus(true);
        deptService.checkDeptId(checkDeptDTO);

        SeCreateUserDTO seCreateUserDTO = new SeCreateUserDTO();
        BeanUtils.copyProperties(dto, seCreateUserDTO);
        return userService.create(seCreateUserDTO);
    }

}
