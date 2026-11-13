package com.zly.skeleton.common.web.config.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class ThreadPoolConfig {
    @Bean("bizThreadPool")
    public ExecutorService bizThreadPool() {
        return new ThreadPoolExecutor(
                5,
                10,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
}
