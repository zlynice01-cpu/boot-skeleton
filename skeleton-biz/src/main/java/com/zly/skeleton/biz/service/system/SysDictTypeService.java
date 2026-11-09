package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.dict.dto.CreateDictTypeDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictTypePageDTO;
import com.zly.skeleton.biz.domain.dict.dto.EditDictTypeDTO;
import com.zly.skeleton.biz.domain.dict.vo.DictTypeListItemVO;
import com.zly.skeleton.biz.domain.dict.vo.DictTypePageItemVO;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysDictType;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 字典类型服务
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
public interface SysDictTypeService extends IService<SysDictType>
{
    Result<PageResponse<DictTypePageItemVO>> getDictTypePage(@Valid DictTypePageDTO dto);

    Result<List<DictTypeListItemVO>> getAllDictTypeList();

    void create(@Valid CreateDictTypeDTO dto);

    void edit(@Valid EditDictTypeDTO dto);

    void removeDictType(Long id);
}
