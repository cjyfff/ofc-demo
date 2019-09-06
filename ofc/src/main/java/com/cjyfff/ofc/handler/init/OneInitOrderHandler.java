package com.cjyfff.ofc.handler.init;

import java.util.concurrent.TimeUnit;

import com.cjyfff.ofc.common.LockKey;
import com.cjyfff.ofc.common.OfcRedisClient;
import com.cjyfff.ofc.common.OfcRedisClient.LockObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by jiashen on 2019/9/5.
 */
@Component
public class OneInitOrderHandler {

    @Autowired
    private OfcRedisClient ofcRedisClient;

    @Autowired
    private OneInitOrderTransactionalHandler transactionalHandler;

    @Async
    public void run(String orderId) {
        LockObject lockObject = null;
        try {
            lockObject = ofcRedisClient.tryLock(0, 60, TimeUnit.SECONDS, LockKey.getWorkFlowKey(orderId));
            if (! lockObject.isLockSuccess()) {
                return;
            }

            transactionalHandler.handlerInitOrder(orderId);
        } catch (Exception e) {
            if (lockObject != null) {
                ofcRedisClient.tryUnLock(lockObject);
            }
        }

    }
}