package com.zly.skeleton.biz.domain.dict.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 编辑字典类型参数
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class EditDictTypeDTO
{
    @NotNull(message = "缺少字典类型id")
    private Long id;

    @NotEmpty(message = "缺少字典编码")
    private String dictCode;

    @NotEmpty(message = "缺少字典名称")
    private String dictName;

    // 状态 0禁用 1正常
    private Integer status;

    // 备注
    private String remark;

    // 排序号
    private Integer sort;

}
