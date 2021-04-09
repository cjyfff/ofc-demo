package com.cjyfff.ofc.core.handler.audit;

import com.cjyfff.ofc.common.enums.OrderHandleStatus;
import com.cjyfff.ofc.common.enums.OrderStatus;
//import com.cjyfff.ofc.core.dq.DelayQueueServer;
import com.cjyfff.ofc.core.dq.DqAcceptMsgDto;
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
import java.util.concurrent.TimeUnit;

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

//    @Autowired
//    private DelayQueueServer delayQueueServer;

    private static final long AUTO_AUDIT_MILLIS = 300 * 1000;

    @Transactional(rollbackFor = Exception.class)
    public void auditOrder(String orderId) throws Exception {
        log.info("处理订单自动客审数据：{}", orderId);

        // 避免 Redis 节点 down 时锁丢失，加上 DB 乐观锁。假如采用 zk 等强一致性的分布式锁的话，就不用这一步
        if (orderMapper.updateHandleStatus(
            orderId, OrderHandleStatus.NOT_DO.getStatus(), OrderHandleStatus.PROCESSING.getStatus()) <= 0) {
            log.warn("订单：{}正在被处理", orderId);
            return;
        }

        Order order = orderMapper.selectOrderByOrderId(orderId);

        if (! OrderStatus.INIT.getStatus().equals(order.getStatus())) {
            log.warn("订单不是初始化完成状态，不能自动客审：{}", orderId);
            return;
        }

        // 模拟网络耗时
        TimeUnit.SECONDS.sleep(1);

        DqAcceptMsgDto dqReqDto = new DqAcceptMsgDto();
        dqReqDto.setTaskId(UUID.randomUUID().toString());
        dqReqDto.setFunctionName("cloudOrderAutoAuditHandler");
        String params = String.format("{\"orderId\": \"%s\"}", orderId);
        dqReqDto.setParams(params);
        dqReqDto.setRetryCount(new Byte("0"));
        dqReqDto.setRetryInterval(1);
        dqReqDto.setNonceStr(UUID.randomUUID().toString());
        dqReqDto.setDelayTime(10L);

//        delayQueueServer.sendDelayTask(dqReqDto);

        order.setStatus(OrderStatus.AUDITED.getStatus());
        order.setHandleStatus(OrderHandleStatus.FINISH.getStatus());
        order.setUpdateAt(new Date());
        orderMapper.updateByPrimaryKeySelective(order);

        OrderStatusExcLog orderStatusExcLog = orderStatusExcLogMapper.selectByOrderIdAndStatus(orderId, OrderStatus.AUDITED.getStatus());
        if (orderStatusExcLog == null) {
            orderStatusExcLog = new OrderStatusExcLog();
            orderStatusExcLog.setOrderId(orderId);
            orderStatusExcLog.setStatus(OrderStatus.AUDITED .getStatus());
            orderStatusExcLog.setStatusExcTime(1);
            orderStatusExcLog.setCreateAt(new Date());
            orderStatusExcLogMapper.insertSelective(orderStatusExcLog);
        } else {
            orderStatusExcLog.setStatusExcTime(orderStatusExcLog.getStatusExcTime() + 1);
            orderStatusExcLogMapper.updateByPrimaryKeySelective(orderStatusExcLog);
        }
    }
}
