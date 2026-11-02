package com.zly.skeleton.biz.domain.config.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 新增 / 编辑配置参数，带 id 为编辑
 *
 * @author ZLY
 * @since 2026/11/2 10:20
 */
@Data
public class SaveConfigDTO
{
    // 配置id，为空表示新增
    private Long id;

    @NotEmpty(message = "缺少参数名称")
    private String configName;

    @NotEmpty(message = "缺少参数键名")
    private String configKey;

    @NotEmpty(message = "缺少参数值")
    private String configValue;

    // 类型 0系统内置 1自定义
    private Integer configType;

    // 备注
    private String remark;

}
