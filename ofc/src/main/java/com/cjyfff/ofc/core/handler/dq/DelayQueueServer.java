package com.cjyfff.ofc.core.handler.dq;

import com.cjyfff.ofc.common.DefaultWebApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author cjyfff
 * @date 2020/5/23 20:44
 */
@FeignClient(value = "c-delay-queue")
public interface DelayQueueServer {
    @RequestMapping(method = RequestMethod.POST, value = "/dq/acceptMsg")
    DefaultWebApiResult sendDelayTask(@RequestBody DqAcceptMsgDto reqVo);
}
