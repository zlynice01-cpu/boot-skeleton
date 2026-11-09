package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.user.dto.*;
import com.zly.skeleton.biz.domain.user.vo.LoginUserVO;
import com.zly.skeleton.biz.domain.user.vo.RecentlyUserItemVO;
import com.zly.skeleton.biz.domain.user.vo.UserPageItemVO;
import com.zly.skeleton.biz.domain.user.vo.UserProfileVO;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import com.zly.skeleton.common.security.entity.user.dto.SeCreateUserDTO;
import com.zly.skeleton.common.web.entity.Result;
import jakarta.validation.Valid;
import com.zly.skeleton.mybatisplus.entity.PageResponse;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:18
 */
public interface SysUserService extends IService<SysUser>
{

    SysUser getByUsername(String username);

    Result<Void> create(CreateUserDTO dto);

    Result<UserProfileVO> getUserProfile(UserProfileDTO dto);

    Result<LoginUserVO> getLoginUser(UserProfileDTO dto);

    Result<PageResponse<UserPageItemVO>> getPage(@Valid UserPageDTO dto);

    Result<Void> edit(@Valid EditUserProfileDTO dto);

    Long getUserCount();

    Result<List<RecentlyUserItemVO>> getRecentlyUserPage(@Valid RecentlyUserDTO dto);

    Result<Integer> enable(@Valid EnableUserDTO dto);
}
    