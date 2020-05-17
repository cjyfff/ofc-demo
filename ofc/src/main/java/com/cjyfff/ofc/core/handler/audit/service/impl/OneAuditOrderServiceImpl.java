package com.cjyfff.ofc.core.handler.audit.service.impl;

import com.alibaba.fastjson.JSON;
import com.cjyfff.ofc.core.handler.audit.service.OneAuditOrderService;
import com.cjyfff.ofc.common.DefaultWebApiResult;
import com.cjyfff.ofc.core.handler.audit.vo.OrderAutoAuditHandlerParaVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @author cjyfff
 * @date 2020/5/17 12:11
 */
@Slf4j
@Service(version = "1.0.0", protocol = { "dubbo", "rest" })
@Path("/")
public class OneAuditOrderServiceImpl implements OneAuditOrderService {

    @Override
    @Path("auditOrder")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String auditOrder(OrderAutoAuditHandlerParaVo reqVo) {
        log.info("处理订单自动客审数据：{}", reqVo.getOrderId());
        // todo: 补全逻辑
        DefaultWebApiResult result = DefaultWebApiResult.success();
        return JSON.toJSONString(result);
    }
}
