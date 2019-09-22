package com.cjyfff.ofc.core.handler.audit;

import com.cjyfff.ofc.common.enums.OrderStatus;
import com.cjyfff.ofc.core.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jiashen
 * @date 9/22/19 11:18 AM
 */
@Component
public class AuditOrdersHandler {

    @Autowired
    private OneAuditOrderHandler oneAuditOrderHandler;

    @Autowired
    private OrderMapper orderMapper;

    @Async("getOrdersExecutor")
    public void run() {
        List<String> allInitOrders = getAllInitOrders();

        for (String orderId : allInitOrders) {
            oneAuditOrderHandler.run(orderId);
        }
    }


    private List<String> getAllInitOrders() {
        return orderMapper.selectOrderIdsByStatus(OrderStatus.INIT.getStatus());
    }
}
