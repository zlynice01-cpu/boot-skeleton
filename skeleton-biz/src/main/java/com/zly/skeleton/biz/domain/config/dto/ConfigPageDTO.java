package com.zly.skeleton.biz.domain.config.dto;

import com.zly.skeleton.mybatisplus.entity.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置分页查询参数
 *
 * @author ZLY
 * @since 2026/11/2 10:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigPageDTO extends PageRequest
{
    // 参数名称
    private String configName;

    // 参数键名
    private String configKey;

    // 类型 0系统内置 1自定义
    private Integer configType;

}
