package com.cjyfff.ofc.handler.init;

import java.util.ArrayList;
import java.util.List;

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

    @Async
    public void run() {
        List<String> allInitOrders = getAllInitOrders();

        for (String orderId : allInitOrders) {
            oneInitOrderHandler.run(orderId);
        }
    }


    private List<String> getAllInitOrders() {
        return new ArrayList<>();
    }

}
