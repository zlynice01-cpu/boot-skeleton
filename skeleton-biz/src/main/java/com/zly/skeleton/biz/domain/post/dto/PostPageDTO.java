package com.zly.skeleton.biz.domain.post.dto;

import com.zly.skeleton.mybatisplus.entity.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 岗位分页查询参数
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostPageDTO extends PageRequest
{
    // 岗位编码
    private String postCode;

    // 岗位名称
    private String postName;

    // 状态 0正常 1停用
    private String status;

}
