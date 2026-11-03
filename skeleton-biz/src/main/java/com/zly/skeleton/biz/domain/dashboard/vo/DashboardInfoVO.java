package com.zly.skeleton.biz.domain.dashboard.vo;

import lombok.Data;

/**
 *
 * @author ZLY
 * @since 2026/11/3 14:53
 */
@Data
public class DashboardInfoVO
{
    private Long totalUser;

    private Long totalRole;

    private Long totalMenu;

    private Long totalConfig;

    public DashboardInfoVO()
    {
    }

    public DashboardInfoVO(Long totalUser, Long totalRole, Long totalMenu, Long totalConfig)
    {
        this.totalUser = totalUser;
        this.totalRole = totalRole;
        this.totalMenu = totalMenu;
        this.totalConfig = totalConfig;
    }

}
