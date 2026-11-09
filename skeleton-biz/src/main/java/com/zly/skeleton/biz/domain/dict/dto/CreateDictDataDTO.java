package com.zly.skeleton.biz.domain.dict.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 新增字典数据参数
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class CreateDictDataDTO
{
    @NotNull(message = "缺少字典类型id")
    private Long dictTypeId;

    @NotEmpty(message = "缺少字典标签")
    private String dictLabel;

    @NotEmpty(message = "缺少字典值")
    private String dictValue;

    // 自定义css样式
    private String cssClass;

    // 前端标签类型 success/danger/warning
    private String listClass;

    // 排序号
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;

    // 备注
    private String remark;

}
