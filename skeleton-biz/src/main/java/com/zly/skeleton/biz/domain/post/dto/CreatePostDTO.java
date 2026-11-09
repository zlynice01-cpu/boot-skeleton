package com.zly.skeleton.biz.domain.post.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 新增岗位参数
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
@Data
public class CreatePostDTO
{
    @NotEmpty(message = "缺少岗位编码")
    private String postCode;

    @NotEmpty(message = "缺少岗位名称")
    private String postName;

    // 排序
    private Integer sort;

    // 状态 0正常 1停用
    private String status;

    // 备注
    private String remark;

}
