package com.cjyfff.ofc.core.handler.audit;

import com.cjyfff.ofc.common.OfcTaskLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author jiashen
 * @date 9/22/19 11:15 AM
 */
@Slf4j
@Component
@JobHandler(value = "auditOrderJob")
public class AuditOrderJob extends IJobHandler {

    @Autowired
    private AuditOrdersHandler auditOrdersHandler;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        OfcTaskLog.info("开始订单客审任务......");
        auditOrdersHandler.run();
        return SUCCESS;
    }
}
