package com.cjyfff.ofc.core.handler.stock;

import java.util.concurrent.TimeUnit;

import com.cjyfff.ofc.common.LockKey;
import com.cjyfff.ofc.common.OfcRedisClient;
import com.cjyfff.ofc.common.OfcRedisClient.LockObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by jiashen on 2019/10/18.
 */
@Slf4j
@Component
public class OneOrderStockHandler {
    @Autowired
    private OfcRedisClient ofcRedisClient;

    @Autowired
    private OneOrderStockTransactionalHandler transactionalHandler;

    @Async("orderStockExecutor")
    public void run(String orderId) {
        log.info("开始订单库存预占：{}", orderId);
        LockObject lockObject = null;
        try {
            lockObject = ofcRedisClient.tryLock(0, 60, TimeUnit.SECONDS, LockKey.getWorkFlowKey(orderId));
            if (! lockObject.isLockSuccess()) {
                log.info("订单：{}，取不到预占库存阶段的锁", orderId);
                return;
            }

            transactionalHandler.initOrder(orderId);
        } catch (Exception e) {
            log.error("OrdersStockHandler get error:", e);
        } finally {
            if (lockObject != null && lockObject.isLockSuccess()) {
                ofcRedisClient.tryUnLock(lockObject);
            }
        }

    }
}
