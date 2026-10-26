package com.zly.skeleton.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ZLY
 * @since 2026/10/26 14:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dept")
public class SysDept extends BaseEntity
{

    @TableId(type = IdType.AUTO)
    private Long id;

    // 父部门ID
    private Long parentId;

    // 部门名称
    private String deptName;

    // 排序
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;

}
