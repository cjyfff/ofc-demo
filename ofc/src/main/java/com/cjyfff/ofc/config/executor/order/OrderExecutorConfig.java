package com.cjyfff.ofc.config.executor.order;

import java.util.concurrent.Executor;

import com.cjyfff.ofc.common.OrderExecutorRejectPolicy;
import com.cjyfff.ofc.config.executor.OrderExecutor;
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
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setRejectedExecutionHandler(new OrderExecutorRejectPolicy());
        taskExecutor.setThreadNamePrefix("initOrderT-");

        taskExecutor.initialize();

        OrderExecutor.initOrderExecutor = taskExecutor;
        return taskExecutor;
    }

    @Bean
    public Executor auditOrderExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(30);
        taskExecutor.setMaxPoolSize(40);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setRejectedExecutionHandler(new OrderExecutorRejectPolicy());
        taskExecutor.setThreadNamePrefix("auditOrderT-");

        taskExecutor.initialize();

        OrderExecutor.auditOrderExecutor = taskExecutor;
        return taskExecutor;
    }

    @Bean
    public Executor orderStockExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(30);
        taskExecutor.setMaxPoolSize(40);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setRejectedExecutionHandler(new OrderExecutorRejectPolicy());
        taskExecutor.setThreadNamePrefix("orderStockExecutor-");

        taskExecutor.initialize();

        OrderExecutor.orderStockExecutor = taskExecutor;
        return taskExecutor;
    }

    @Bean
    public Executor sendOrderWmsExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(30);
        taskExecutor.setMaxPoolSize(40);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setRejectedExecutionHandler(new OrderExecutorRejectPolicy());
        taskExecutor.setThreadNamePrefix("sendWmsExecutor-");

        taskExecutor.initialize();

        OrderExecutor.sendOrderWmsExecutor = taskExecutor;
        return taskExecutor;
    }
}
