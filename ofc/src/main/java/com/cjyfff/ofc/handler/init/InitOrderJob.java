package com.cjyfff.ofc.handler.init;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jiashen on 2019/9/5.
 */
@JobHandler(value = "initOrderJob")
public class InitOrderJob extends IJobHandler {

    @Autowired
    private InitOrdersHandler initOrdersHandler;

    @Override
    public ReturnT<String> execute(String param) throws Exception {

        initOrdersHandler.run();
        return SUCCESS;
    }
}
