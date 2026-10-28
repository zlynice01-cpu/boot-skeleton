package com.zly.skeleton.biz.domain.user.vo;

import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/10/27 16:05
 */
@Data
public class UserPageItemVO
{

    // 用户id
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
    private String deptName;

}
