package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.dict.dto.CreateDictTypeDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictTypePageDTO;
import com.zly.skeleton.biz.domain.dict.dto.EditDictTypeDTO;
import com.zly.skeleton.biz.domain.dict.vo.DictTypeListItemVO;
import com.zly.skeleton.biz.domain.dict.vo.DictTypePageItemVO;
import com.zly.skeleton.biz.enums.StatusEnum;
import com.zly.skeleton.biz.mapper.SysDictDataMapper;
import com.zly.skeleton.biz.mapper.SysDictTypeMapper;
import com.zly.skeleton.biz.service.system.SysDictTypeService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 字典类型服务实现
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService
{
    private final SysDictTypeMapper dictTypeMapper;
    private final SysDictDataMapper dictDataMapper;

    @Override
    public Result<PageResponse<DictTypePageItemVO>> getDictTypePage(DictTypePageDTO dto)
    {
        String dictCode = dto.getDictCode();
        String dictName = dto.getDictName();
        Integer status = dto.getStatus();

        IPage<SysDictType> page = PageUtil.createPage(dto);
        IPage<DictTypePageItemVO> voPage = dictTypeMapper
                .selectPage(page, Wrappers.lambdaQuery(SysDictType.class)
                        .eq(StringUtils.isNotBlank(dictCode), SysDictType::getDictCode, dictCode)
                        .like(StringUtils.isNotBlank(dictName), SysDictType::getDictName, dictName)
                        .eq(status != null, SysDictType::getStatus, status)
                        .orderByAsc(SysDictType::getSort)
                        .orderByAsc(SysDictType::getId))
                .convert(DictTypePageItemVO::entityToVO);

        PageResponse<DictTypePageItemVO> voResult = PageUtil.getPageResult(voPage);

        return Result.success(voResult);
    }

    @Override
    public Result<List<DictTypeListItemVO>> getAllDictTypeList()
    {
        LambdaQueryWrapper<SysDictType> queryWrapper = Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getStatus, StatusEnum.YES.getCode())
                .orderByAsc(SysDictType::getSort)
                .orderByAsc(SysDictType::getId);
        List<SysDictType> dictTypes = dictTypeMapper.selectList(queryWrapper);
        List<DictTypeListItemVO> voList = dictTypes.stream().filter(Objects::nonNull)
                .map(item -> {
                    DictTypeListItemVO vo = new DictTypeListItemVO();
                    BeanUtils.copyProperties(item, vo);
                    return vo;
                }).toList();
        return Result.success(voList);
    }

    @Override
    public void create(CreateDictTypeDTO dto)
    {
        checkDictCode(dto.getDictCode(), null);

        SysDictType dictType = new SysDictType();
        BeanUtils.copyProperties(dto, dictType);
        save(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(EditDictTypeDTO dto)
    {
        SysDictType dbDictType = getById(dto.getId());
        if (dbDictType == null)
        {
            throw new BizException(BizErrorEnum.DICT_TYPE_NOT_FOUND);
        }
        checkDictCode(dto.getDictCode(), dto.getId());

        SysDictType dictType = new SysDictType();
        BeanUtils.copyProperties(dto, dictType);
        updateById(dictType);

        // 字典编码在数据表中冗余存储，编码变更时同步刷新
        if (!Objects.equals(dbDictType.getDictCode(), dto.getDictCode()))
        {
            dictDataMapper.update(null, Wrappers.lambdaUpdate(SysDictData.class)
                    .set(SysDictData::getDictCode, dto.getDictCode())
                    .eq(SysDictData::getDictTypeId, dto.getId()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDictType(Long id)
    {
        SysDictType dbDictType = getById(id);
        if (dbDictType == null)
        {
            throw new BizException(BizErrorEnum.DICT_TYPE_NOT_FOUND);
        }

        removeById(id);
        // 级联删除该类型下的字典数据
        dictDataMapper.delete(Wrappers.lambdaQuery(SysDictData.class)
                .eq(SysDictData::getDictTypeId, id));
    }

    /**
     * 校验字典编码唯一，id 不为空时排除自身
     */
    private void checkDictCode(String dictCode, Long id)
    {
        List<SysDictType> dictTypes = dictTypeMapper.selectList(Wrappers.lambdaQuery(SysDictType.class)
                .eq(SysDictType::getDictCode, dictCode));
        if (ObjectUtils.isEmpty(dictTypes))
        {
            return;
        }
        boolean conflict = dictTypes.stream().filter(Objects::nonNull)
                .anyMatch(item -> !item.getId().equals(id));
        if (conflict)
        {
            throw new BizException(BizErrorEnum.DICT_CODE_EXIST);
        }
    }
}
