package com.zly.skeleton.biz.domain.dept.vo;

import com.zly.skeleton.mybatisplus.entity.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author ZLY
 * @since 2026/10/30 11:29
 */
@Data
public class DeptListItemVO
{

    private Long id;

    // 父部门ID
    private Long parentId;

    // 部门名称
    private String deptName;

    // 排序
    private Integer sort;

    // 状态 0禁用 1正常
    private Integer status;
    public static DeptListItemVO entityToVO(SysDept sysDept)
    {
        if (sysDept == null)
        {
            return null;
        }
        DeptListItemVO vo = new DeptListItemVO();
        BeanUtils.copyProperties(sysDept, vo);
        return vo;
    }
}
