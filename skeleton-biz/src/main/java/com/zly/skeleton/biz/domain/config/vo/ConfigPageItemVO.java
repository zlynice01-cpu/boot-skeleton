package com.zly.skeleton.biz.domain.config.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zly.skeleton.mybatisplus.entity.SysConfig;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 配置分页列表项
 *
 * @author ZLY
 * @since 2026/11/2 10:20
 */
@Data
public class ConfigPageItemVO
{
    // 配置id
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static ConfigPageItemVO entityToVO(SysConfig config) {
        if (config == null)
        {
            return null;
        }
        ConfigPageItemVO vo = new ConfigPageItemVO();
        BeanUtils.copyProperties(config, vo);
        return vo;
    }
}
