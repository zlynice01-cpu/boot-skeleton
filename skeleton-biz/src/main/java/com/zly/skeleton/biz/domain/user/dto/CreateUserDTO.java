package com.zly.skeleton.biz.domain.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/10/26 11:17
 */
@Data
public class CreateUserDTO
{
    // 用户名
    @NotEmpty(message = "缺少用户名")
    private String username;

    // 密码
    private String password;

    // 昵称
    @NotEmpty(message = "缺少昵称")
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
    @NotNull(message = "缺少部门信息")
    private Long deptId;
}
