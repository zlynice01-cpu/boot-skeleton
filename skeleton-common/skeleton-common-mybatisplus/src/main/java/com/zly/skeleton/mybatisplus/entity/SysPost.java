package com.zly.skeleton.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位表
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_post")
public class SysPost extends BaseEntity
{

    @TableId(type = IdType.AUTO)
    private Long id;

    // 岗位编码（唯一，如 manager、staff）
    private String postCode;

    // 岗位名称
    private String postName;

    // 排序
    private Integer sort;

    // 状态 0禁用 1启用
    private Integer status;

    // 备注
    private String remark;

}
