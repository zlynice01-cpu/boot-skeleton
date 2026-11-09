package com.zly.skeleton.biz.domain.dept.dto;

import lombok.Data;

/**
 * 部门列表查询参数
 *
 * @author ZLY
 * @since 2026/10/30 11:29
 */
@Data
public class DeptListDTO
{
    // 状态 0禁用 1启用，为空时查询全部
    private Integer status;

}
