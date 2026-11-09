package com.zly.skeleton.core.controller.system;

import com.zly.skeleton.biz.domain.dict.dto.CreateDictDataDTO;
import com.zly.skeleton.biz.domain.dict.dto.DeleteDictDataDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictDataListDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictDataPageDTO;
import com.zly.skeleton.biz.domain.dict.dto.EditDictDataDTO;
import com.zly.skeleton.biz.domain.dict.vo.DictDataListItemVO;
import com.zly.skeleton.biz.domain.dict.vo.DictDataPageItemVO;
import com.zly.skeleton.biz.service.system.SysDictDataService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@RestController
@RequestMapping("/dictData")
@RequiredArgsConstructor
public class SysDictDataController
{
    private final SysDictDataService dictDataService;

    @GetMapping("/list")
    public Result<List<DictDataListItemVO>> list(DictDataListDTO dto) {
        return dictDataService.getDictDataList(dto);
    }

    @PostMapping("/page")
    public Result<PageResponse<DictDataPageItemVO>> page(@Valid @RequestBody DictDataPageDTO dto) {
        return dictDataService.getDictDataPage(dto);
    }

    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody CreateDictDataDTO dto) {
        dictDataService.create(dto);
        return Result.success();
    }

    @PostMapping("/edit")
    public Result<Void> edit(@Valid @RequestBody EditDictDataDTO dto) {
        dictDataService.edit(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody DeleteDictDataDTO dto) {
        dictDataService.removeDictData(dto.getId());
        return Result.success();
    }

}
