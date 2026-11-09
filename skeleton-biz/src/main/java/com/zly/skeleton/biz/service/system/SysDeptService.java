package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.dept.dto.CheckDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.CreateDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.DeptListDTO;
import com.zly.skeleton.biz.domain.dept.dto.EditDeptDTO;
import com.zly.skeleton.biz.domain.dept.dto.EditDeptStatusDTO;
import com.zly.skeleton.biz.domain.dept.vo.DeptListItemVO;
import com.zly.skeleton.common.web.entity.Result;
import com.zly.skeleton.mybatisplus.entity.SysDept;

import java.util.List;

/**
 *
 * @author ZLY
 * @since 2026/10/26 14:08
 */
public interface SysDeptService extends IService<SysDept>
{

    Result<List<DeptListItemVO>> getAllDeptList(DeptListDTO dto);

    void create(CreateDeptDTO dto);

    void edit(EditDeptDTO dto);

    void updateStatus(EditDeptStatusDTO dto);

    void removeDept(Long id);

    void checkDeptId(CheckDeptDTO checkDept);

    Result<Integer> enableDept(Long id);

    void updateStatus(Long id, Integer status);
}
