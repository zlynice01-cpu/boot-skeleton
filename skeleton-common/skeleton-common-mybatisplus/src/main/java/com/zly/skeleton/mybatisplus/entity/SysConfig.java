package com.zly.skeleton.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置表
 *
 * @author ZLY
 * @since 2026/11/2 10:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_config")
public class SysConfig extends BaseEntity
{

    @TableId(type = IdType.AUTO)
    private Long id;

    // 参数名称
    private String configName;

    // 参数键名
    private String configKey;

    // 参数值
    private String configValue;

    // 类型 0系统内置 1自定义
    private Integer configType;

    // 备注
    private String remark;

}
