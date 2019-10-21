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
public class AcceptJobExecutorConfig {
    @Bean
    public Executor acceptJobExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(0);
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.setRejectedExecutionHandler(new AbortPolicy());
        taskExecutor.setThreadNamePrefix("acceptJobExecutor-");

        taskExecutor.initialize();
        return taskExecutor;
    }
}
