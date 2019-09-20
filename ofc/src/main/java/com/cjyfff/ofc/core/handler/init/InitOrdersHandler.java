package com.cjyfff.ofc.core.handler.init;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cjyfff.ofc.common.CommonUtils;
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

    @Async("getOrdersExecutor")
    public void run() {
        List<String> allInitOrders = getAllInitOrders();

        for (String orderId : allInitOrders) {
            oneInitOrderHandler.run(orderId);
        }
    }


    private List<String> getAllInitOrders() {
        List<String> orderIds = new ArrayList<>();
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        for (int i = 0; i < 1000 ; i++) {
            String o = "D" + date + CommonUtils.lpad(String.valueOf(i), 3, "0");
            orderIds.add(o);
        }
        return orderIds;
    }



}
