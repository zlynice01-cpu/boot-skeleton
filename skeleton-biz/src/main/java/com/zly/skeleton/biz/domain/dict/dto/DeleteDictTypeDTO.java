package com.zly.skeleton.biz.domain.dict.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 删除字典类型参数
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class DeleteDictTypeDTO
{
    @NotNull(message = "缺少字典类型id")
    private Long id;

}
