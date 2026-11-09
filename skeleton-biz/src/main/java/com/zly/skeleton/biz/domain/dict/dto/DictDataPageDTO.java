package com.zly.skeleton.biz.domain.dict.dto;

import com.zly.skeleton.mybatisplus.entity.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据分页查询参数
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictDataPageDTO extends PageRequest
{
    // 字典类型id
    private Long dictTypeId;

    // 字典编码
    private String dictCode;

    // 字典标签
    private String dictLabel;

    // 状态 0禁用 1正常
    private Integer status;

}
