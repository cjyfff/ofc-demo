package com.cjyfff.ofc.core.handler.init;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.cjyfff.ofc.common.enums.OrderHandleStatus;
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
 * Created by jiashen on 2019/9/6.
 */
@Slf4j
@Component
public class OneInitOrderTransactionalHandler {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusExcLogMapper orderStatusExcLogMapper;

    @Transactional(rollbackFor = Exception.class)
    public void initOrder(String orderId) throws Exception {
        log.info("处理订单初始化数据：{}", orderId);

        // 避免 Redis 节点 down 时锁丢失，加上 DB 乐观锁。假如采用 zk 等强一致性的分布式锁的话，就不用这一步
        if (orderMapper.updateHandleStatus(
            orderId, OrderHandleStatus.NOT_DO.getStatus(), OrderHandleStatus.PROCESSING.getStatus()) <= 0) {
            log.warn("订单：{}正在被处理", orderId);
            return;
        }

        Order order = orderMapper.selectOrderByOrderId(orderId);

        if (! OrderStatus.NEW.getStatus().equals(order.getStatus())) {
            log.warn("订单不是新订单状态，不能初始化：{}", orderId);
            return;
        }

        // 模拟网络耗时
        TimeUnit.SECONDS.sleep(1);

        order.setStatus(OrderStatus.INIT.getStatus());
        order.setUpdateAt(new Date());
        order.setHandleStatus(OrderHandleStatus.FINISH.getStatus());
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
