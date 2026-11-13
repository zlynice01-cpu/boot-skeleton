package com.zly.skeleton.common.web.config.threadpool;

import jakarta.annotation.Nonnull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author ZLY
 * @since 2026/11/11 11:56
 */
public class ThreadPoolFactory implements ThreadFactory
{
    private final AtomicInteger count = new AtomicInteger(1);
    @Override
    public Thread newThread(@Nonnull Runnable r)
    {
        return new Thread(r, "biz-thread-" + count.getAndIncrement());
    }
}
