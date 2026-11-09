package com.zly.skeleton.biz.domain.dict.dto;

import lombok.Data;

/**
 * 字典数据列表查询参数（按字典编码取下拉/标签用数据）
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class DictDataListDTO
{
    // 字典编码
    private String dictCode;

    // 状态 0禁用 1正常，为空表示全部
    private Integer status;

}
