package com.cjyfff.ofc.core.handler.sendwms;

import com.cjyfff.ofc.common.OfcTaskLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@JobHandler(value = "sendWmsJob")
public class SendWmsJob extends IJobHandler {
    @Autowired
    private SendWmsHandler sendWmsHandler;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        OfcTaskLog.info("开始发送订单到WMS任务......");
        sendWmsHandler.run();
        return SUCCESS;
    }
}
