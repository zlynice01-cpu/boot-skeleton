package com.zly.skeleton.biz.domain.role.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 *
 * @author ZLY
 * @since 2026/10/30 15:19
 */
@Data
public class RolePageItemVO
{
    private Long id;

    // 角色名称
    private String roleName;

    // 角色标识，如 admin、common
    private String roleKey;

    // 排序号
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static RolePageItemVO entityToVO(SysRole role) {
        if (role == null)
        {
            return null;
        }
        RolePageItemVO vo = new RolePageItemVO();
        BeanUtils.copyProperties(role, vo);
        return vo;
    }
}
