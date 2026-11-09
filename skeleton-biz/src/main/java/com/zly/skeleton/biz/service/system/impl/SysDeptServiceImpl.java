package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.domain.dept.dto.CheckDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.CreateDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.DeptListDTO;
import com.zly.skeleton.biz.domain.dept.dto.EditDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.EditDeptStatusDTO;
import com.zly.skeleton.biz.domain.dept.vo.DeptListItemVO;
import com.zly.skeleton.biz.mapper.SysDeptMapper;
import com.zly.skeleton.biz.service.system.SysDeptService;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.common.web.enums.BizErrorEnum;
import com.zly.skeleton.common.web.exception.BizException;
import com.zly.skeleton.mybatisplus.entity.SysDept;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author ZLY
 * @since 2026/10/26 14:23
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService
{
    // 状态 0正常 1停用
    private static final String STATUS_NORMAL = "0";

    private static final String STATUS_DISABLED = "1";

    private final SysDeptMapper deptMapper;

    @Override
    public Result<List<DeptListItemVO>> getAllDeptList(DeptListDTO dto)
    {
        String status = dto.getStatus();
        List<SysDept> deptList = deptMapper.selectList(Wrappers.lambdaQuery(SysDept.class)
                .eq(StringUtils.isNotBlank(status), SysDept::getStatus, status)
                .orderByAsc(SysDept::getSort)
                .orderByAsc(SysDept::getId));

        List<DeptListItemVO> voList = deptList.stream()
                .filter(Objects::nonNull)
                .map(DeptListItemVO::entityToVO)
                .toList();

        return Result.success(voList);
    }

    @Override
    public void create(CreateDeptDTO dto)
    {
        SysDept dept = new SysDept();
        BeanUtils.copyProperties(dto, dept);
        if (dept.getParentId() == null)
        {
            dept.setParentId(0L);
        }
        if (StringUtils.isBlank(dept.getStatus()))
        {
            dept.setStatus(STATUS_NORMAL);
        }
        if (dept.getSort() == null)
        {
            dept.setSort(0);
        }
        save(dept);
    }

    @Override
    public void edit(EditDeptDTO dto)
    {
        SysDept dbDept = getById(dto.getId());
        if (dbDept == null)
        {
            throw new BizException(BizErrorEnum.DEPT_NOT_FOUND);
        }

        Long parentId = dto.getParentId() == null ? 0L : dto.getParentId();
        if (Objects.equals(parentId, dto.getId()))
        {
            throw new BizException(BizErrorEnum.DEPT_PARENT_ERROR);
        }

        SysDept dept = new SysDept();
        BeanUtils.copyProperties(dto, dept);
        dept.setParentId(parentId);
        updateById(dept);
    }

    @Override
    public void updateStatus(EditDeptStatusDTO dto)
    {
        SysDept dbDept = getById(dto.getId());
        if (dbDept == null)
        {
            throw new BizException(BizErrorEnum.DEPT_NOT_FOUND);
        }

        deptMapper.update(null, Wrappers.lambdaUpdate(SysDept.class)
                .set(SysDept::getStatus, dto.getStatus())
                .eq(SysDept::getId, dto.getId()));
    }

    @Override
    public void removeDept(Long id)
    {
        SysDept dbDept = getById(id);
        if (dbDept == null)
        {
            throw new BizException(BizErrorEnum.DEPT_NOT_FOUND);
        }

        // 存在下级部门时不允许删除，避免出现游离节点
        long childCount = count(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getParentId, id));
        if (childCount > 0)
        {
            throw new BizException(BizErrorEnum.DEPT_HAS_CHILDREN);
        }

        removeById(id);
    }

    @Override
    public void checkDeptId(@NotNull CheckDeptDTO checkDept)
    {
        SysDept deptEntity = getById(checkDept.getDeptId());
        if (deptEntity == null) {
            throw new BizException(BizErrorEnum.DEPT_NOT_FOUND);
        }
        Boolean checkStatus = checkDept.getCheckStatus();
        if (Boolean.TRUE.equals(checkStatus) && STATUS_DISABLED.equals(deptEntity.getStatus())) {
            throw new BizException(BizErrorEnum.DEPT_EXPIRE);
        }
    }
}
