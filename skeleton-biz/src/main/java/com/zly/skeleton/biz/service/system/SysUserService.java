package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.user.dto.UserPageDTO;
import com.zly.skeleton.biz.domain.user.dto.UserProfileDTO;
import com.zly.skeleton.biz.domain.user.vo.UserPageItemVO;
import com.zly.skeleton.biz.domain.user.vo.UserProfileVO;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import com.zly.skeleton.common.security.entity.user.dto.SeCreateUserDTO;
import com.zly.skeleton.common.web.entity.Result;
import jakarta.validation.Valid;
import com.zly.skeleton.mybatisplus.entity.PageResponse;

/**
 *
 * @author ZLY
 * @since 2026/10/20 14:18
 */
public interface SysUserService extends IService<SysUser>
{

    SysUser getByUsername(String username);

    Result<Void> create(SeCreateUserDTO dto);

    Result<UserProfileVO> getUserProfile(UserProfileDTO dto);

    Result<PageResponse<UserPageItemVO>> getPage(@Valid UserPageDTO dto);

}
    