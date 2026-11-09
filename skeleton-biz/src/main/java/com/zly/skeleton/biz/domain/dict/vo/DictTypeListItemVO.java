package com.zly.skeleton.biz.domain.dict.vo;

import lombok.Data;

/**
 * 字典类型列表项（下拉用）
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class DictTypeListItemVO
{
    // 字典类型id
    private Long id;

    // 字典编码
    private String dictCode;

    // 字典名称
    private String dictName;

}
