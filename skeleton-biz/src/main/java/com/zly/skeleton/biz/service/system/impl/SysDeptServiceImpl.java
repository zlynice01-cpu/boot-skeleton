package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.dept.dto.CheckDeptDTO;
import com.zly.skeleton.biz.enums.StatusEnum;
import com.zly.skeleton.biz.mapper.SysDeptMapper;
import com.zly.skeleton.biz.service.system.SysDeptService;
import com.zly.skeleton.common.web.enums.BizErrorEnum;
import com.zly.skeleton.common.web.exception.BizException;
import com.zly.skeleton.mybatisplus.entity.SysDept;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author ZLY
 * @since 2026/10/26 14:23
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService
{
    private final SysDeptMapper deptMapper;

    @Override
    public void checkDeptId(@NotNull CheckDeptDTO checkDept)
    {
        SysDept deptEntity = getById(checkDept.getDeptId());
        if (deptEntity == null) {
            throw new BizException(BizErrorEnum.DEPT_NOT_FOUND);
        }
        Boolean checkStatus = checkDept.getCheckStatus();
        Integer status = deptEntity.getStatus();
        if (Boolean.TRUE.equals(checkStatus) && StatusEnum.NO.getCode().equals(status)) {
            throw new BizException(BizErrorEnum.DEPT_EXPIRE);
        }
    }
}
