package com.zly.skeleton.biz.domain.dict.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 删除字典数据参数
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class DeleteDictDataDTO
{
    @NotNull(message = "缺少字典数据id")
    private Long id;

}
