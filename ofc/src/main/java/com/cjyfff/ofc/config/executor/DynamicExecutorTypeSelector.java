package com.cjyfff.ofc.config.executor;

import com.google.common.collect.ImmutableMap;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;

public class DynamicExecutorTypeSelector {
    private static final Map<Integer, ThreadPoolTaskExecutor> EXECUTOR_TYPE_MAP =
            ImmutableMap.<Integer, ThreadPoolTaskExecutor>builder()
                    .put(1, OrderExecutor.initOrderExecutor)
                    .put(2, OrderExecutor.auditOrderExecutor)
                    .put(3, OrderExecutor.orderStockExecutor)
                    .put(4, OrderExecutor.sendOrderWmsExecutor)
                    .build();

    public static ThreadPoolTaskExecutor getExecutorByType(Integer type) {
        return EXECUTOR_TYPE_MAP.get(type);
    }
}
