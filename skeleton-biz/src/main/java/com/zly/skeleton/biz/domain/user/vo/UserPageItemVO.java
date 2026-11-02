package com.zly.skeleton.biz.domain.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
