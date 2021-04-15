package com.cjyfff.ofc.core.service;

import com.cjyfff.ofc.config.executor.OrderExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


@Service
public class DynamicExecutorConfigService {

    public void changeConfig(Integer type, Integer corePoolSize, Integer maxPoolSize, Integer queueCapacity) {
        checkParams(corePoolSize, maxPoolSize, queueCapacity);

        ThreadPoolTaskExecutor executor;
        switch (type) {
            case 1:
                executor = OrderExecutor.initOrderExecutor;
                break;
            case 2:
                executor = OrderExecutor.auditOrderExecutor;
                break;
            case 3:
                executor = OrderExecutor.orderStockExecutor;
                break;
            case 4:
                executor = OrderExecutor.sendOrderWmsExecutor;
                break;
            default:
                throw new IllegalArgumentException("Illegal executor type");
        }

        if (corePoolSize != null) {
            executor.setCorePoolSize(corePoolSize);
        }
        if (maxPoolSize != null) {
            executor.setMaxPoolSize(maxPoolSize);
        }
        if (queueCapacity != null) {
            executor.setQueueCapacity(queueCapacity);
        }
    }

    private void checkParams(Integer corePoolSize, Integer maxPoolSize, Integer queueCapacity) {
        if (corePoolSize != null && corePoolSize <= 0) {
            throw new IllegalArgumentException("Illegal corePoolSize");
        }
        if (maxPoolSize != null && maxPoolSize <= 0) {
            throw new IllegalArgumentException("Illegal maxPoolSize");
        }
        if (queueCapacity != null && queueCapacity <= 0) {
            throw new IllegalArgumentException("Illegal queueCapacity");
        }
    }
}
