package com.cjyfff.ofc.config.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by jiashen on 2019/9/6.
 */
@Configuration
public class InitOrderExecutorConfig {
    @Bean
    public Executor initOrderExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(15);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.setRejectedExecutionHandler(new AbortPolicy());
        taskExecutor.setThreadNamePrefix("initOrderExecutor-");

        taskExecutor.initialize();
        return taskExecutor;
    }
}
