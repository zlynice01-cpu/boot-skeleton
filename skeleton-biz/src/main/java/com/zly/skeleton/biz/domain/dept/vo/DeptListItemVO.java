package com.zly.skeleton.biz.domain.dept.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zly.skeleton.mybatisplus.entity.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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

    // 负责人姓名
    private String leader;

    // 负责人用户ID
    private Long leaderUserId;

    // 联系电话
    private String phone;

    // 状态 0禁用 1启用
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

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
