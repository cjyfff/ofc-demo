package com.cjyfff.ofc.core.handler.init;

import com.cjyfff.ofc.common.OfcTaskLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiashen on 2019/9/5.
 */
@Slf4j
@Component
@JobHandler(value = "initOrderJob")
public class InitOrderJob extends IJobHandler {

    @Autowired
    private InitOrdersHandler initOrdersHandler;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        OfcTaskLog.info("开始初始化订单任务......");
        initOrdersHandler.run();
        return SUCCESS;
    }
}
