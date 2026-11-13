package com.zly.skeleton.core.handler.log;

import com.zly.skeleton.biz.service.system.SysOperLogService;
import com.zly.skeleton.biz.service.system.SysUserService;
import com.zly.skeleton.common.security.util.UserContextUtil;
import com.zly.skeleton.core.entity.OperLogInfo;
import com.zly.skeleton.mybatisplus.entity.SysOperLog;
import com.zly.skeleton.mybatisplus.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * 操作日志处理器
 *
 * @author ZLY
 * @since 2026/11/11 11:28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OperLogHandler
{
    private final SysUserService userService;
    private final SysOperLogService operLogService;

    public void handle(OperLogInfo operLogInfo)
    {
        String username = UserContextUtil.getUsername();
        SysUser user = userService.getByUsername(username);
        operLogInfo.setOperName(username);
        operLogInfo.setOperNickname(user.getNickname());
        operLogInfo.setOperDeptId(user.getDeptId());
        SysOperLog sysOperLog = new SysOperLog();
        BeanUtils.copyProperties(operLogInfo, sysOperLog);
        operLogService.save(sysOperLog);
    }
}
