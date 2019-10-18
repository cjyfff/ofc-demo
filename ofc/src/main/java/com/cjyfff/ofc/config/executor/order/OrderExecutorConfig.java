package com.cjyfff.ofc.config.executor.order;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by jiashen on 19-9-12.
 */
@Configuration
public class OrderExecutorConfig {
    @Bean
    public Executor initOrderExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(30);
        taskExecutor.setMaxPoolSize(40);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setKeepAliveSeconds(10);
        taskExecutor.setRejectedExecutionHandler(new AbortPolicy());
        taskExecutor.setThreadNamePrefix("initOrderT-");

        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean
    public Executor auditOrderExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(30);
        taskExecutor.setMaxPoolSize(40);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setKeepAliveSeconds(10);
        taskExecutor.setRejectedExecutionHandler(new AbortPolicy());
        taskExecutor.setThreadNamePrefix("auditOrderT-");

        taskExecutor.initialize();
        return taskExecutor;
    }
}
