package com.cjyfff.ofc;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cjyfff.ofc.core.mapper.OrderMapper;
import com.cjyfff.ofc.core.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by jiashen on 19-9-20.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitRandomOrdersTests {

    @Autowired
    private OrderMapper orderMapper;

    private final static int TOTAL_ORDERS = 1000;

    private final static int ORDER_STATUS = 300;

    //@Test
    public void initOrders() {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        for (int i = 0; i < TOTAL_ORDERS; i++) {
            Order order = new Order();
            String orderId = "D" + date + String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 9 - 1)));
            order.setOrderId(orderId);
            order.setStatus(ORDER_STATUS);
            order.setCreateAt(new Date());
            order.setUpdateAt(new Date());
            orderMapper.insertSelective(order);
        }
    }
}
