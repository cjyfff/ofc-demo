package com.cjyfff.ofc.core.handler.audit.service.impl;

import com.alibaba.fastjson.JSON;
import com.cjyfff.ofc.core.handler.audit.service.OneAuditOrderService;
import com.cjyfff.ofc.core.vo.DefaultWebApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author cjyfff
 * @date 2020/5/17 12:11
 */
@Slf4j
@Service(version = "1.0.0", protocol = "dubbo")
public class OneAuditOrderServiceImpl implements OneAuditOrderService {

    public String auditOrder(String orderId) {
        log.info("处理订单自动客审数据：{}", orderId);
        // todo: 补全逻辑
        DefaultWebApiResult result = DefaultWebApiResult.success();
        return JSON.toJSONString(result);
    }
}
