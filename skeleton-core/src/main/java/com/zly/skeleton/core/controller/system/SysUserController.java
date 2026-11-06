package com.zly.skeleton.core.controller.system;

import com.zly.skeleton.biz.domain.dept.dto.CheckDeptDTO;
import com.zly.skeleton.biz.domain.user.dto.*;
import com.zly.skeleton.biz.domain.user.vo.LoginUserVO;
import com.zly.skeleton.biz.domain.user.vo.RecentlyUserItemVO;
import com.zly.skeleton.biz.domain.user.vo.UserPageItemVO;
import com.zly.skeleton.biz.domain.user.vo.UserProfileVO;
import com.zly.skeleton.biz.service.system.SysDeptService;
import com.zly.skeleton.biz.service.system.SysUserService;
import com.zly.skeleton.common.security.util.UserContextUtil;
import com.zly.skeleton.common.web.entity.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.zly.skeleton.mybatisplus.entity.PageResponse;

import java.util.List;

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
    public Result<Void> create(@Valid @RequestBody CreateUserDTO dto)
    {
        Long deptId = dto.getDeptId();
        CheckDeptDTO checkDeptDTO = new CheckDeptDTO();
        checkDeptDTO.setDeptId(deptId);
        checkDeptDTO.setCheckStatus(true);
        deptService.checkDeptId(checkDeptDTO);

        return userService.create(dto);
    }

    @PostMapping("/profile")
    // @PreAuthorize("hasAuthority('system:user:list')")
    public Result<UserProfileVO> profile(@Valid @RequestBody UserProfileDTO dto)
    {
        return userService.getUserProfile(dto);
    }

    @PostMapping("/getLoginUser")
    public Result<LoginUserVO> getLoginUser()
    {
        String username = UserContextUtil.getUsername();
        UserProfileDTO dto = new UserProfileDTO();
        dto.setUsername(username);
        return userService.getLoginUser(dto);
    }

    @PostMapping("/page")
    // @PreAuthorize("hasAuthority('system:user:list')")
    public Result<PageResponse<UserPageItemVO>> getPage(@Valid @RequestBody UserPageDTO dto) {
        return userService.getPage(dto);
    }

    @PostMapping("/recentlyUser")
    public Result<List<RecentlyUserItemVO>> getRecentlyUserPage(@Valid @RequestBody RecentlyUserDTO dto) {
        return userService.getRecentlyUserPage(dto);
    }

    @PostMapping("/edit")
    public Result<Void> edit(@Valid @RequestBody EditUserProfileDTO dto) {
        return userService.edit(dto);
    }

    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody DeleteUserDTO dto) {
        userService.removeById(dto.getId());
        return Result.success();
    }


}
