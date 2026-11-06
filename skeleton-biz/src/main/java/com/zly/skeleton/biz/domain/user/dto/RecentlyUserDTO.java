package com.zly.skeleton.biz.domain.user.dto;

import com.zly.skeleton.mybatisplus.entity.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author ZLY
 * @since 2026/11/6 9:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RecentlyUserDTO extends PageRequest
{
    private LocalDateTime createTime;

    public void initParam(int day) {
        this.createTime = LocalDate.now().atStartOfDay().minusDays(day);
    }
}
