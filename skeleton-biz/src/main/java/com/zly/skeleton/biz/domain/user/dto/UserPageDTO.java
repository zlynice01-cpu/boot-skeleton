package com.zly.skeleton.biz.domain.user.dto;

import com.zly.skeleton.mybatisplus.entity.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

}
