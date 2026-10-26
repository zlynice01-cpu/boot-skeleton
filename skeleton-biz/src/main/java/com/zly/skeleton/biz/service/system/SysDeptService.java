package com.zly.skeleton.biz.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zly.skeleton.biz.domain.dept.dto.CheckDeptDTO;
import com.zly.skeleton.mybatisplus.entity.SysDept;

/**
 *
 * @author ZLY
 * @since 2026/10/26 14:08
 */
public interface SysDeptService extends IService<SysDept>
{

    void checkDeptId(CheckDeptDTO checkDept);

}
