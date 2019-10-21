package com.cjyfff.ofc.config.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by jiashen on 2019/10/18.
 */
@Configuration
public class OrderStockExecutorConfig {
    @Bean
    public Executor orderStockExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(15);
        taskExecutor.setMaxPoolSize(20);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setKeepAliveSeconds(30);
        taskExecutor.setRejectedExecutionHandler(new AbortPolicy());
        taskExecutor.setThreadNamePrefix("orderStockExecutor-");

        taskExecutor.initialize();
        return taskExecutor;
    }
}
