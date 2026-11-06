package com.zly.skeleton.biz.domain.user.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.zly.skeleton.mybatisplus.entity.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 *
 * @author ZLY
 * @since 2026/10/27 16:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPageDTO extends PageRequest
{
    // 部门id
    private Long deptId;

    // 昵称
    private String nickname;

    // 账号
    private String username;

    // 手机号
    private String phone;

    // 状态
    private Integer status;

    private LocalDateTime createTime;

}
