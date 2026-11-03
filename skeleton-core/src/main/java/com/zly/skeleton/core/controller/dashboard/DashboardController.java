package com.zly.skeleton.core.controller.dashboard;

import com.zly.skeleton.biz.domain.dashboard.vo.DashboardInfoVO;
import com.zly.skeleton.biz.service.system.DashboardService;
import com.zly.skeleton.common.web.entity.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ZLY
 * @since 2026/11/3 14:52
 */
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController
{
    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public Result<DashboardInfoVO> summary() {
        DashboardInfoVO vo = dashboardService.summary();
        return Result.success(vo);
    }
}
