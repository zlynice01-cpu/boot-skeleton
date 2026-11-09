package com.zly.skeleton.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict_data")
public class SysDictData extends BaseEntity
{

    @TableId(type = IdType.AUTO)
    private Long id;

    // 字典类型ID，关联 sys_dict_type.id
    private Long dictTypeId;

    // 冗余字典编码，减少联表查询
    private String dictCode;

    // 字典展示标签：正常、锁定
    private String dictLabel;

    // 字典存储值：1、0
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
