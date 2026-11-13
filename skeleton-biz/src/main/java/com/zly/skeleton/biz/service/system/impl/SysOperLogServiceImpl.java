package com.zly.skeleton.biz.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zly.skeleton.biz.mapper.SysOperLogMapper;
import com.zly.skeleton.biz.service.system.SysOperLogService;
import com.zly.skeleton.mybatisplus.entity.SysOperLog;
import org.springframework.stereotype.Service;

/**
 *
 * @author ZLY
 * @since 2026/11/12 19:08
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService
{
}
