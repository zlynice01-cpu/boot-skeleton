package com.zly.skeleton.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型表
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict_type")
public class SysDictType extends BaseEntity
{

    @TableId(type = IdType.AUTO)
    private Long id;

    // 字典编码（唯一key，如 sys_user_status）
    private String dictCode;

    // 字典名称，如：用户状态
    private String dictName;

    // 状态 0禁用 1正常
    private Integer status;

    // 备注
    private String remark;

    // 排序号
    private Integer sort;

}
