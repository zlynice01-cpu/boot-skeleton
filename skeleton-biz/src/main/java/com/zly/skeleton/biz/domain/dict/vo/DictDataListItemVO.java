package com.zly.skeleton.biz.domain.dict.vo;

import lombok.Data;

/**
 * 字典数据列表项（前端下拉 / 标签渲染用）
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Data
public class DictDataListItemVO
{
    // 字典数据id
    private Long id;

    // 字典标签
    private String dictLabel;

    // 字典值
    private String dictValue;

    // 自定义css样式
    private String cssClass;

    // 前端标签类型 success/danger/warning
    private String listClass;

    // 排序号
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;

}
