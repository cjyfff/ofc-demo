package com.cjyfff.ofc.core.handler;

import java.util.concurrent.TimeUnit;

import com.cjyfff.ofc.common.OfcTaskLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;


@JobHandler(value="demoJobHandler")
@Component
public class DemoJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        OfcTaskLog.info("XXL-JOB, Hello World.");

        for (int i = 0; i < 5; i++) {
            OfcTaskLog.info("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return SUCCESS;
    }

}