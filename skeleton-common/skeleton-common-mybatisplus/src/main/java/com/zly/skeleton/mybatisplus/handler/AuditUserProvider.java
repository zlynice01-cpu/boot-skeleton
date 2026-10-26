package com.zly.skeleton.mybatisplus.handler;

/**
 * 当前操作人提供者。
 * <p>
 * 本模块只依赖此抽象，由上层模块（如 security）提供实现，
 * 避免 mybatisplus 反向依赖 security 造成模块循环。
 *
 * @author ZLY
 */
public interface AuditUserProvider
{
    /**
     * 当前操作人ID，未登录（如登录接口自身）返回 null
     *
     * @return 用户ID
     */
    Long getCurrentUserId();
}
