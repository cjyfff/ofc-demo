package com.cjyfff.ofc.core.handler.audit.service;

import com.cjyfff.ofc.core.handler.audit.vo.OrderAutoAuditHandlerParaVo;

public interface OneAuditOrderService {

    String auditOrder(OrderAutoAuditHandlerParaVo reqVo);
}
