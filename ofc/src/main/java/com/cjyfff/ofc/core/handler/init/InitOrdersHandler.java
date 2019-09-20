package com.cjyfff.ofc.core.handler.init;

import java.util.List;

import com.cjyfff.ofc.common.enums.OrderStatus;
import com.cjyfff.ofc.core.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by jiashen on 2019/9/5.
 */
@Component
public class InitOrdersHandler {

    @Autowired
    private OneInitOrderHandler oneInitOrderHandler;

    @Autowired
    private OrderMapper orderMapper;

    @Async("getOrdersExecutor")
    public void run() {
        List<String> allInitOrders = getAllInitOrders();

        for (String orderId : allInitOrders) {
            oneInitOrderHandler.run(orderId);
        }
    }


    private List<String> getAllInitOrders() {
        return orderMapper.selectOrderIdsByStatus(OrderStatus.NEW.getStatus());
    }



}
