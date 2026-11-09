package com.zly.skeleton.core.controller.company;

import com.zly.skeleton.biz.domain.dept.dto.CreateDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.DeleteDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.DeptListDTO;
import com.zly.skeleton.biz.domain.dept.dto.EditDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.EditDeptStatusDTO;
import com.zly.skeleton.biz.domain.dept.vo.DeptListItemVO;
import com.zly.skeleton.biz.service.system.SysDeptService;
import com.zly.skeleton.common.web.entity.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 14:08
 */
@RestController
@RequestMapping("/dept")
@RequiredArgsConstructor
public class SysDeptController
{
    private final SysDeptService deptService;

    @GetMapping("/list")
    public Result<List<DeptListItemVO>> list(@Valid DeptListDTO dto) {
        return deptService.getAllDeptList(dto);
    }

    @PostMapping("/create")
    public Result<Void> create(@Valid @RequestBody CreateDeptDTO dto) {
        deptService.create(dto);
        return Result.success();
    }

    @PostMapping("/edit")
    public Result<Void> edit(@Valid @RequestBody EditDeptDTO dto) {
        deptService.edit(dto);
        return Result.success();
    }

    @PostMapping("/status")
    public Result<Void> updateStatus(@Valid @RequestBody EditDeptStatusDTO dto) {
        deptService.updateStatus(dto);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result<Void> delete(@Valid @RequestBody DeleteDeptDTO dto) {
        deptService.removeDept(dto.getId());
        return Result.success();
    }

    @GetMapping("/enable")
    public Result<Integer> enable(Long id) {
        return deptService.enableDept(id);
    }

}
