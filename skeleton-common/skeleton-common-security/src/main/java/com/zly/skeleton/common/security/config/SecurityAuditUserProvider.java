package com.zly.skeleton.common.security.config;

import com.zly.skeleton.common.security.util.UserContextUtil;
import com.zly.skeleton.mybatisplus.handler.AuditUserProvider;
import org.springframework.stereotype.Component;

/**
 * 为 mybatisplus 模块提供当前操作人，切断模块循环依赖
 *
 * @author ZLY
 */
@Component
public class SecurityAuditUserProvider implements AuditUserProvider
{
    @Override
    public Long getCurrentUserId()
    {
        return UserContextUtil.getUserId();
    }
}
