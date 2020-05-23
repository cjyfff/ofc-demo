package com.cjyfff.ofc.core.handler.audit;

import com.cjyfff.ofc.common.enums.OrderStatus;
import com.cjyfff.ofc.core.handler.dq.DelayQueueServer;
import com.cjyfff.ofc.core.handler.dq.DqAcceptMsgDto;
import com.cjyfff.ofc.core.mapper.OrderMapper;
import com.cjyfff.ofc.core.mapper.OrderStatusExcLogMapper;
import com.cjyfff.ofc.core.model.Order;
import com.cjyfff.ofc.core.model.OrderStatusExcLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author jiashen
 * @date 9/22/19 11:22 AM
 */
@Slf4j
@Component
public class OneAuditOrderTransactionalHandler {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusExcLogMapper orderStatusExcLogMapper;

    @Autowired
    private DelayQueueServer delayQueueServer;

    private static final long AUTO_AUDIT_MILLIS = 300 * 1000;

    @Transactional(rollbackFor = Exception.class)
    public void auditOrder(String orderId) throws Exception {
        log.info("处理订单自动客审数据：{}", orderId);

        Order order = orderMapper.selectOrderByOrderId(orderId);

        if (! OrderStatus.INIT.getStatus().equals(order.getStatus())) {
            log.warn("订单不是初始化完成状态，不能自动客审：{}", orderId);
            return;
        }

        DqAcceptMsgDto dqReqDto = new DqAcceptMsgDto();
        dqReqDto.setTaskId(UUID.randomUUID().toString());
        dqReqDto.setFunctionName("cloudOrderAutoAuditHandler");
        String params = String.format("{\"orderId\": \"%s\"}", orderId);
        dqReqDto.setParams(params);
        dqReqDto.setRetryCount(new Byte("0"));
        dqReqDto.setRetryInterval(1);
        dqReqDto.setNonceStr(UUID.randomUUID().toString());
        dqReqDto.setDelayTime(10L);

        delayQueueServer.sendDelayTask(dqReqDto);

        order.setStatus(OrderStatus.AUTO_AUDITING.getStatus());
        order.setUpdateAt(new Date());
        orderMapper.updateByPrimaryKeySelective(order);

        OrderStatusExcLog orderStatusExcLog = orderStatusExcLogMapper.selectByOrderIdAndStatus(orderId, OrderStatus.AUTO_AUDITING.getStatus());
        if (orderStatusExcLog == null) {
            orderStatusExcLog = new OrderStatusExcLog();
            orderStatusExcLog.setOrderId(orderId);
            orderStatusExcLog.setStatus(OrderStatus.AUTO_AUDITING .getStatus());
            orderStatusExcLog.setStatusExcTime(1);
            orderStatusExcLog.setCreateAt(new Date());
            orderStatusExcLogMapper.insertSelective(orderStatusExcLog);
        } else {
            orderStatusExcLog.setStatusExcTime(orderStatusExcLog.getStatusExcTime() + 1);
            orderStatusExcLogMapper.updateByPrimaryKeySelective(orderStatusExcLog);
        }
    }
}
