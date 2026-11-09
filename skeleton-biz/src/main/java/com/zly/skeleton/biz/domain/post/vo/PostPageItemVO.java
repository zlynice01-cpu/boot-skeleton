package com.zly.skeleton.biz.domain.post.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zly.skeleton.mybatisplus.entity.SysPost;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 岗位分页列表项
 *
 * @author ZLY
 * @since 2026/11/9 10:30
 */
@Data
public class PostPageItemVO
{
    // 岗位id
    private Long id;

    // 岗位编码
    private String postCode;

    // 岗位名称
    private String postName;

    // 排序
    private Integer sort;

    // 状态 0禁用 1启用
    private Integer status;

    // 备注
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static PostPageItemVO entityToVO(SysPost post)
    {
        if (post == null)
        {
            return null;
        }
        PostPageItemVO vo = new PostPageItemVO();
        BeanUtils.copyProperties(post, vo);
        return vo;
    }
}
