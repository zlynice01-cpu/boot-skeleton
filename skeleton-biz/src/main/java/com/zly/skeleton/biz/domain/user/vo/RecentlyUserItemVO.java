package com.zly.skeleton.biz.domain.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 *
 * @author ZLY
 * @since 2026/11/6 9:50
 */
@Data
public class RecentlyUserItemVO
{
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
