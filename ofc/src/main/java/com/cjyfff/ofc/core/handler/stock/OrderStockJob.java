package com.cjyfff.ofc.core.handler.stock;

import com.cjyfff.ofc.common.OfcTaskLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by jiashen on 2019/10/18.
 */
@Slf4j
@Component
@JobHandler(value = "orderStockJob")
public class OrderStockJob extends IJobHandler {
    @Autowired
    private OrdersStockHandler ordersStockHandler;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        OfcTaskLog.info("开始订单预占库存任务......");
        ordersStockHandler.run();
        return SUCCESS;
    }
}
