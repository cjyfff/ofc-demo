package com.cjyfff.ofc.config.executor;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Component
public class OrderExecutor {
    public static ThreadPoolTaskExecutor initOrderExecutor;

    public static ThreadPoolTaskExecutor auditOrderExecutor;

    public static ThreadPoolTaskExecutor orderStockExecutor;

    public static ThreadPoolTaskExecutor sendOrderWmsExecutor;
}
