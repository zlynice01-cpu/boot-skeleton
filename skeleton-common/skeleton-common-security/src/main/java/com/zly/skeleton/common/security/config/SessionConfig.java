package com.zly.skeleton.common.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;

/**
 * Spring Session 配置：HttpSession 持久化到 Redis，支持多实例共享登录态
 * <p>
 * 使用 EnableRedisIndexedHttpSession 而非 EnableRedisHttpSession：
 * 前者启用 principalName 索引，配合 SpringSessionBackedSessionRegistry 实现跨实例互踢
 *
 * @author ZLY
 */
@Configuration
@EnableRedisIndexedHttpSession(
        maxInactiveIntervalInSeconds = 1800,
        redisNamespace = "skeleton:session"
)
public class SessionConfig
{
}
