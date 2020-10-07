package com.cjyfff.ofc.core.handler.sendwms;

import com.cjyfff.ofc.common.LockKey;
import com.cjyfff.ofc.common.OfcRedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OneOrderSendWmsHandler {
    @Autowired
    private OfcRedisClient ofcRedisClient;

    @Autowired
    private OneOrderTransactionalSendWmsHandler transactionalHandler;

    @Async("sendOrderWmsExecutor")
    public void run(String orderId) {
        log.info("开始发送单个订单到WMS：{}", orderId);
        OfcRedisClient.LockObject lockObject = null;
        try {
            lockObject = ofcRedisClient.tryLock(0, 60, TimeUnit.SECONDS, LockKey.getWorkFlowKey(orderId));
            if (! lockObject.isLockSuccess()) {
                log.info("订单：{}，取不到已分配库存阶段的锁", orderId);
                return;
            }

            transactionalHandler.initOrder(orderId);
        } catch (Exception e) {
            log.error("OneOrderSendWmsHandler get error:", e);
        } finally {
            if (lockObject != null && lockObject.isLockSuccess()) {
                ofcRedisClient.tryUnLock(lockObject);
            }
        }

    }
}
