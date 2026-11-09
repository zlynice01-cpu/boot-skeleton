package com.zly.skeleton.biz.domain.dict.dto;

import com.zly.skeleton.mybatisplus.entity.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型分页查询参数
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypePageDTO extends PageRequest
{
    // 字典编码
    private String dictCode;

    // 字典名称
    private String dictName;

    // 状态 0禁用 1正常
    private Integer status;

}
