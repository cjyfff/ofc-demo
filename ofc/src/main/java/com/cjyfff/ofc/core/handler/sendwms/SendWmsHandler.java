package com.cjyfff.ofc.core.handler.sendwms;

import com.cjyfff.ofc.common.enums.OrderStatus;
import com.cjyfff.ofc.core.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendWmsHandler {

    @Autowired
    private OneOrderSendWmsHandler oneOrderSendWmsHandler;

    @Autowired
    private OrderMapper orderMapper;

    @Async("getOrdersExecutor")
    public void run() {
        List<String> allNewOrders = getAllNewOrders();

        for (String orderId : allNewOrders) {
            oneOrderSendWmsHandler.run(orderId);
        }
    }


    private List<String> getAllNewOrders() {
        return orderMapper.selectOrderIdsByStatus(OrderStatus.STOCK.getStatus());
    }
}
