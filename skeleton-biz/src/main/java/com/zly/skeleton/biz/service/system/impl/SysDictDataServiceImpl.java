package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.dict.dto.CreateDictDataDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictDataListDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictDataPageDTO;
import com.zly.skeleton.biz.domain.dict.dto.EditDictDataDTO;
import com.zly.skeleton.biz.domain.dict.vo.DictDataListItemVO;
import com.zly.skeleton.biz.domain.dict.vo.DictDataPageItemVO;
import com.zly.skeleton.biz.mapper.SysDictDataMapper;
import com.zly.skeleton.biz.mapper.SysDictTypeMapper;
import com.zly.skeleton.biz.service.system.SysDictDataService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.enums.BizErrorEnum;
import com.zly.skeleton.common.web.exception.BizException;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysDictData;
import com.zly.skeleton.mybatisplus.entity.SysDictType;
import com.zly.skeleton.mybatisplus.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 字典数据服务实现
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService
{
    private final SysDictDataMapper dictDataMapper;
    private final SysDictTypeMapper dictTypeMapper;

    @Override
    public Result<PageResponse<DictDataPageItemVO>> getDictDataPage(DictDataPageDTO dto)
    {
        Long dictTypeId = dto.getDictTypeId();
        String dictCode = dto.getDictCode();
        String dictLabel = dto.getDictLabel();
        Integer status = dto.getStatus();

        IPage<SysDictData> page = PageUtil.createPage(dto);
        IPage<DictDataPageItemVO> voPage = dictDataMapper
                .selectPage(page, Wrappers.lambdaQuery(SysDictData.class)
                        .eq(dictTypeId != null, SysDictData::getDictTypeId, dictTypeId)
                        .eq(StringUtils.isNotBlank(dictCode), SysDictData::getDictCode, dictCode)
                        .like(StringUtils.isNotBlank(dictLabel), SysDictData::getDictLabel, dictLabel)
                        .eq(status != null, SysDictData::getStatus, status)
                        .orderByAsc(SysDictData::getSort)
                        .orderByAsc(SysDictData::getId))
                .convert(DictDataPageItemVO::entityToVO);

        PageResponse<DictDataPageItemVO> voResult = PageUtil.getPageResult(voPage);

        return Result.success(voResult);
    }

    @Override
    public Result<List<DictDataListItemVO>> getDictDataList(DictDataListDTO dto)
    {
        String dictCode = dto.getDictCode();
        Integer status = dto.getStatus();

        List<SysDictData> dictDataList = dictDataMapper.selectList(Wrappers.lambdaQuery(SysDictData.class)
                .eq(StringUtils.isNotBlank(dictCode), SysDictData::getDictCode, dictCode)
                .eq(status != null, SysDictData::getStatus, status)
                .orderByAsc(SysDictData::getSort)
                .orderByAsc(SysDictData::getId));
        List<DictDataListItemVO> voList = dictDataList.stream().filter(Objects::nonNull)
                .map(item -> {
                    DictDataListItemVO vo = new DictDataListItemVO();
                    BeanUtils.copyProperties(item, vo);
                    return vo;
                }).toList();
        return Result.success(voList);
    }

    @Override
    public void create(CreateDictDataDTO dto)
    {
        SysDictType dictType = getDictType(dto.getDictTypeId());

        SysDictData dictData = new SysDictData();
        BeanUtils.copyProperties(dto, dictData);
        // 字典编码以类型为准，避免数据表冗余字段不一致
        dictData.setDictCode(dictType.getDictCode());
        save(dictData);
    }

    @Override
    public void edit(EditDictDataDTO dto)
    {
        SysDictData dbDictData = getById(dto.getId());
        if (dbDictData == null)
        {
            throw new BizException(BizErrorEnum.DICT_DATA_NOT_FOUND);
        }

        SysDictData dictData = new SysDictData();
        BeanUtils.copyProperties(dto, dictData);
        if (dto.getDictTypeId() != null)
        {
            SysDictType dictType = getDictType(dto.getDictTypeId());
            dictData.setDictCode(dictType.getDictCode());
        }
        updateById(dictData);
    }

    @Override
    public void removeDictData(Long id)
    {
        SysDictData dbDictData = getById(id);
        if (dbDictData == null)
        {
            throw new BizException(BizErrorEnum.DICT_DATA_NOT_FOUND);
        }
        removeById(id);
    }

    /**
     * 查询字典类型，不存在则抛出业务异常
     */
    private SysDictType getDictType(Long dictTypeId)
    {
        SysDictType dictType = dictTypeId == null ? null : dictTypeMapper.selectById(dictTypeId);
        if (dictType == null)
        {
            throw new BizException(BizErrorEnum.DICT_TYPE_NOT_FOUND);
        }
        return dictType;
    }
}
