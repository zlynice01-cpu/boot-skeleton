package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.config.dto.ConfigPageDTO;
import com.zly.skeleton.biz.domain.config.dto.SaveConfigDTO;
import com.zly.skeleton.biz.domain.config.dto.SysConfigDTO;
import com.zly.skeleton.biz.domain.config.vo.ConfigPageItemVO;
import com.zly.skeleton.biz.mapper.SysConfigMapper;
import com.zly.skeleton.biz.service.system.SysConfigService;
import com.zly.skeleton.cache.util.RedisCache;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.enums.BizErrorEnum;
import com.zly.skeleton.common.web.exception.BizException;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysConfig;
import com.zly.skeleton.mybatisplus.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 系统配置服务实现
 *
 * @author ZLY
 * @since 2026/11/2 10:20
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService
{
    // 配置缓存前缀：sys_config:{configKey}
    private static final String CACHE_KEY_PREFIX = "sys_config:";

    // 系统内置类型
    private static final Integer CONFIG_TYPE_BUILT_IN = 0;

    private final SysConfigMapper configMapper;
    private final RedisCache redisCache;

    @Override
    public Result<PageResponse<ConfigPageItemVO>> getConfigPage(ConfigPageDTO dto)
    {
        String configName = dto.getConfigName();
        String configKey = dto.getConfigKey();
        Integer configType = dto.getConfigType();

        IPage<SysConfig> page = PageUtil.createPage(dto);
        IPage<ConfigPageItemVO> voPage = configMapper
                .selectPage(page, Wrappers.lambdaQuery(SysConfig.class)
                        .like(StringUtils.isNotBlank(configName), SysConfig::getConfigName, configName)
                        .like(StringUtils.isNotBlank(configKey), SysConfig::getConfigKey, configKey)
                        .eq(configType != null, SysConfig::getConfigType, configType)
                        .orderByDesc(SysConfig::getId))
                .convert(ConfigPageItemVO::entityToVO);

        PageResponse<ConfigPageItemVO> voResult = PageUtil.getPageResult(voPage);

        return Result.success(voResult);
    }

    @Override
    public void saveConfig(SaveConfigDTO dto)
    {
        String configKey = dto.getConfigKey();
        SysConfig dbConfig = getByConfigKey(configKey);
        Long id = dto.getId();
        if (dbConfig != null && !dbConfig.getId().equals(id))
        {
            throw new BizException(BizErrorEnum.CONFIG_KEY_EXIST);
        }

        SysConfig config = new SysConfig();
        BeanUtils.copyProperties(dto, config);
        if (id == null)
        {
            save(config);
        } else
        {
            updateById(config);
        }
        redisCache.setCacheObject(CACHE_KEY_PREFIX + configKey, config.getConfigValue());
    }

    @Override
    public void deleteConfig(Long id)
    {
        SysConfig dbConfig = getById(id);
        if (dbConfig == null)
        {
            throw new BizException(BizErrorEnum.CONFIG_NOT_FOUND);
        }
        if (CONFIG_TYPE_BUILT_IN.equals(dbConfig.getConfigType()))
        {
            throw new BizException(BizErrorEnum.CONFIG_BUILT_IN_NOT_DELETE);
        }

        removeById(id);
        redisCache.deleteObject(CACHE_KEY_PREFIX + dbConfig.getConfigKey());
    }

    @Override
    public void refreshCache()
    {
        Collection<String> keys = redisCache.keys(CACHE_KEY_PREFIX + "*");
        if (ObjectUtils.isNotEmpty(keys))
        {
            redisCache.deleteObject(keys);
        }

        List<SysConfig> configList = list();
        for (SysConfig config : configList)
        {
            redisCache.setCacheObject(CACHE_KEY_PREFIX + config.getConfigKey(), config.getConfigValue());
        }
    }

    @Override
    public SysConfigDTO getConfigByCode(String code)
    {
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers
                .lambdaQuery(SysConfig.class)
                .eq(SysConfig::getConfigKey, code);
        SysConfig sysConfig = getOne(queryWrapper);
        SysConfigDTO dto = new SysConfigDTO();
        BeanUtils.copyProperties(sysConfig, dto);
        return dto;
    }

    @Override
    public Long getConfigCount()
    {
        LambdaQueryWrapper<SysConfig> lambdaQuery = Wrappers.lambdaQuery(SysConfig.class);
        return count(lambdaQuery);
    }

    /**
     * 按参数键名查询配置，未找到返回 null
     */
    private SysConfig getByConfigKey(String configKey)
    {
        LambdaQueryWrapper<SysConfig> queryWrapper = Wrappers.lambdaQuery(SysConfig.class)
                .eq(SysConfig::getConfigKey, configKey);
        List<SysConfig> configList = configMapper.selectList(queryWrapper);
        if (ObjectUtils.isEmpty(configList))
        {
            return null;
        }
        return configList.getFirst();
    }
}
