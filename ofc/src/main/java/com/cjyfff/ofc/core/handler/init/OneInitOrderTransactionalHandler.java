package com.cjyfff.ofc.core.handler.init;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by jiashen on 2019/9/6.
 */
@Slf4j
@Component
public class OneInitOrderTransactionalHandler {
    //@Transactional(rollbackFor = Exception.class)
    public void handlerInitOrder(String orderId) throws Exception {
        log.info("处理订单数据：{}", orderId);
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
