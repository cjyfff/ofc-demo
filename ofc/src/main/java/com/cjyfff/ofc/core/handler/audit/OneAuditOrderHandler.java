package com.cjyfff.ofc.core.handler.audit;

import com.cjyfff.ofc.common.LockKey;
import com.cjyfff.ofc.common.OfcRedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jiashen
 * @date 9/22/19 11:18 AM
 */
@Slf4j
@Component
public class OneAuditOrderHandler {

    @Autowired
    private OfcRedisClient ofcRedisClient;

    @Autowired
    private OneAuditOrderTransactionalHandler transactionalHandler;

    @Async("auditOrderExecutor")
    public void run(String orderId) {
        log.info("开始订单自动客审：{}", orderId);
        OfcRedisClient.LockObject lockObject = null;
        try {
            lockObject = ofcRedisClient.tryLock(0, 60, TimeUnit.SECONDS, LockKey.getWorkFlowKey(orderId));
            if (! lockObject.isLockSuccess()) {
                log.info("订单：{}，取不到客审阶段的锁", orderId);
                return;
            }

            transactionalHandler.auditOrder(orderId);
        } catch (Exception e) {
            log.error("OneAuditOrderHandler get error:", e);
        } finally {
            if (lockObject != null) {
                ofcRedisClient.tryUnLock(lockObject);
            }
        }

    }
}
