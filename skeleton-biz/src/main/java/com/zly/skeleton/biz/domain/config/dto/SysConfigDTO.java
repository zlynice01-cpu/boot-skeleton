package com.zly.skeleton.biz.domain.config.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author ZLY
 * @since 2026/11/3 14:09
 */
@Data
public class SysConfigDTO implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

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
