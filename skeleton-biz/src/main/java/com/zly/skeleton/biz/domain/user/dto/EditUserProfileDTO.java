package com.zly.skeleton.biz.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/31 18:04
 */
@Data
public class EditUserProfileDTO
{
    @NotNull(message = "用户id不能为空")
    private Long id;

    // 用户名
    private String username;

    // 昵称
    private String nickname;

    // 邮箱
    private String email;

    // 电话号
    private String phone;

    // 头像
    private String avatar;

    // 状态
    private Integer status;

    // 部门id
    private Long deptId;

    // 角色id
    private List<Long> roleIds;

}
