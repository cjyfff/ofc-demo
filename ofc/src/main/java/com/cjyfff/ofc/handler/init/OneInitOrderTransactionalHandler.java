package com.cjyfff.ofc.handler.init;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jiashen on 2019/9/6.
 */
@Component
public class OneInitOrderTransactionalHandler {
    @Transactional(rollbackFor = Exception.class)
    public void handlerInitOrder(String orderId) throws Exception {
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
