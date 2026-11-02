package com.zly.skeleton.core.controller.system;

import com.zly.skeleton.biz.domain.config.dto.ConfigPageDTO;
import com.zly.skeleton.biz.domain.config.dto.DeleteConfigDTO;
import com.zly.skeleton.biz.domain.config.dto.SaveConfigDTO;
import com.zly.skeleton.biz.domain.config.vo.ConfigPageItemVO;
import com.zly.skeleton.biz.service.system.SysConfigService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置
 *
 * @author ZLY
 * @since 2026/11/2 10:20
 */
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class SysConfigController
{
    private final SysConfigService configService;

    @PostMapping("/page")
    public Result<PageResponse<ConfigPageItemVO>> page(@Valid @RequestBody ConfigPageDTO dto) {
        return configService.getConfigPage(dto);
    }

    @PostMapping("/save")
    public Result<Void> save(@Valid @RequestBody SaveConfigDTO dto) {
        configService.saveConfig(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody DeleteConfigDTO dto) {
        configService.deleteConfig(dto.getId());
        return Result.success();
    }

    @PostMapping("/refreshCache")
    public Result<Void> refreshCache() {
        configService.refreshCache();
        return Result.success();
    }

}
