package com.cjyfff.ofc.core.handler.stock;

import java.util.List;
import com.cjyfff.ofc.common.enums.OrderStatus;
import com.cjyfff.ofc.core.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by jiashen on 2019/10/18.
 */
@Slf4j
@Component
public class OrdersStockHandler {
    @Autowired
    private OneOrderStockHandler oneOrderStockHandler;

    @Autowired
    private OrderMapper orderMapper;

    @Async("getOrdersExecutor")
    public void run() {
        List<String> allNewOrders = getAllAuditedOrders();

        for (String orderId : allNewOrders) {
            oneOrderStockHandler.run(orderId);
        }
    }


    private List<String> getAllAuditedOrders() {
        return orderMapper.selectOrderIdsByStatus(OrderStatus.AUDITED.getStatus());
    }
}
