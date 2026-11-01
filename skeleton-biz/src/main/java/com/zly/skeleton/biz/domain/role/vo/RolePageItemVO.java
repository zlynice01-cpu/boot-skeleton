package com.zly.skeleton.biz.domain.role.vo;

import com.zly.skeleton.biz.domain.role.dto.RolePageDTO;
import com.zly.skeleton.mybatisplus.entity.SysRole;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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
