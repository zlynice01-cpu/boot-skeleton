package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.config.dto.ConfigPageDTO;
import com.zly.skeleton.biz.domain.config.dto.SaveConfigDTO;
import com.zly.skeleton.biz.domain.config.dto.SysConfigDTO;
import com.zly.skeleton.biz.domain.config.vo.ConfigPageItemVO;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysConfig;
import jakarta.validation.Valid;

/**
 * 系统配置服务
 *
 * @author ZLY
 * @since 2026/11/2 10:20
 */
public interface SysConfigService extends IService<SysConfig>
{
    Result<PageResponse<ConfigPageItemVO>> getConfigPage(@Valid ConfigPageDTO dto);

    void saveConfig(@Valid SaveConfigDTO dto);

    void deleteConfig(Long id);

    void refreshCache();

    SysConfigDTO getConfigByCode(String code);

    Long getConfigCount();
}
