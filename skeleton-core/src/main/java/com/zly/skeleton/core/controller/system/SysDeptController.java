package com.zly.skeleton.core.controller.system;

import com.zly.skeleton.biz.domain.dept.dto.DeptListDTO;
import com.zly.skeleton.biz.domain.dept.vo.DeptListItemVO;
import com.zly.skeleton.biz.enums.StatusEnum;
import com.zly.skeleton.biz.service.system.SysDeptService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.SysDept;
import com.zly.skeleton.mybatisplus.entity.SysPermission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

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
        List<SysDept> list = deptService.list();
        List<DeptListItemVO> voList = list.stream()
                .filter(Objects::nonNull)
                .filter(item -> StatusEnum.YES.getCode().equals(item.getStatus()))
                .map(DeptListItemVO::entityToVO)
                .toList();
        return Result.success(voList);
    }

}
