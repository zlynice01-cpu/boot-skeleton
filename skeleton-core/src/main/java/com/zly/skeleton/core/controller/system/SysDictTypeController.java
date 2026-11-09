package com.zly.skeleton.core.controller.system;

import com.zly.skeleton.biz.domain.dict.dto.CreateDictTypeDTO;
import com.zly.skeleton.biz.domain.dict.dto.DeleteDictTypeDTO;
import com.zly.skeleton.biz.domain.dict.dto.DictTypePageDTO;
import com.zly.skeleton.biz.domain.dict.dto.EditDictTypeDTO;
import com.zly.skeleton.biz.domain.dict.vo.DictTypeListItemVO;
import com.zly.skeleton.biz.domain.dict.vo.DictTypePageItemVO;
import com.zly.skeleton.biz.service.system.SysDictTypeService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典类型
 *
 * @author ZLY
 * @since 2026/11/6 9:40
 */
@RestController
@RequestMapping("/dictType")
@RequiredArgsConstructor
public class SysDictTypeController
{
    private final SysDictTypeService dictTypeService;

    @GetMapping("/list")
    public Result<List<DictTypeListItemVO>> list() {
        return dictTypeService.getAllDictTypeList();
    }

    @PostMapping("/page")
    public Result<PageResponse<DictTypePageItemVO>> page(@Valid @RequestBody DictTypePageDTO dto) {
        return dictTypeService.getDictTypePage(dto);
    }

    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody CreateDictTypeDTO dto) {
        dictTypeService.create(dto);
        return Result.success();
    }

    @PostMapping("/edit")
    public Result<Void> edit(@Valid @RequestBody EditDictTypeDTO dto) {
        dictTypeService.edit(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody DeleteDictTypeDTO dto) {
        dictTypeService.removeDictType(dto.getId());
        return Result.success();
    }

}
