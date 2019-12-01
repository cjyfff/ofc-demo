package com.cjyfff.ofc.core.handler.stock;

import java.util.Date;

import com.cjyfff.ofc.common.enums.OrderStatus;
import com.cjyfff.ofc.core.mapper.OrderMapper;
import com.cjyfff.ofc.core.mapper.OrderStatusExcLogMapper;
import com.cjyfff.ofc.core.model.Order;
import com.cjyfff.ofc.core.model.OrderStatusExcLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jiashen on 2019/10/18.
 */
@Slf4j
@Component
public class OneOrderStockTransactionalHandler {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusExcLogMapper orderStatusExcLogMapper;

    @Transactional(rollbackFor = Exception.class)
    public void initOrder(String orderId) throws Exception {
        log.info("处理订单库存预占数据：{}", orderId);

        Order order = orderMapper.selectOrderByOrderId(orderId);

        if (! OrderStatus.AUDITED.getStatus().equals(order.getStatus())) {
            log.warn("订单不是审核成功状态，不能预占库存：{}", orderId);
            return;
        }

        order.setStatus(OrderStatus.STOCK.getStatus());
        order.setUpdateAt(new Date());
        orderMapper.updateByPrimaryKeySelective(order);

        OrderStatusExcLog orderStatusExcLog = orderStatusExcLogMapper.selectByOrderIdAndStatus(orderId, OrderStatus.INIT.getStatus());
        if (orderStatusExcLog == null) {
            orderStatusExcLog = new OrderStatusExcLog();
            orderStatusExcLog.setOrderId(orderId);
            orderStatusExcLog.setStatus(OrderStatus.INIT.getStatus());
            orderStatusExcLog.setStatusExcTime(1);
            orderStatusExcLog.setCreateAt(new Date());
            orderStatusExcLogMapper.insertSelective(orderStatusExcLog);
        } else {
            orderStatusExcLog.setStatusExcTime(orderStatusExcLog.getStatusExcTime() + 1);
            orderStatusExcLogMapper.updateByPrimaryKeySelective(orderStatusExcLog);
        }
    }
}