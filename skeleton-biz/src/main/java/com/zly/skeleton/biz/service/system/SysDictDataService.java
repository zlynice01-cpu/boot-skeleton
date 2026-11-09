package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.dict.dto.CreateDictDataDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictDataListDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictDataPageDTO;
import com.zly.skeleton.biz.domain.dict.dto.EditDictDataDTO;
import com.zly.skeleton.biz.domain.dict.vo.DictDataListItemVO;
import com.zly.skeleton.biz.domain.dict.vo.DictDataPageItemVO;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import com.zly.skeleton.mybatisplus.entity.SysDictData;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 字典数据服务
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
public interface SysDictDataService extends IService<SysDictData>
{
    Result<PageResponse<DictDataPageItemVO>> getDictDataPage(@Valid DictDataPageDTO dto);

    Result<List<DictDataListItemVO>> getDictDataList(@Valid DictDataListDTO dto);

    void create(@Valid CreateDictDataDTO dto);

    void edit(@Valid EditDictDataDTO dto);

    void removeDictData(Long id);
}
