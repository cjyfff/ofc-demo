package com.cjyfff.ofc.core.handler.audit;

import com.cjyfff.ofc.common.DefaultWebApiResult;
import com.cjyfff.ofc.core.handler.audit.vo.OrderAutoAuditHandlerParaVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author cjyfff
 * @date 2020/5/23 17:02
 */
@Slf4j
@RestController
public class CloudOneAuditOrderController {

    @RequestMapping(path = "/auditOrder", method={RequestMethod.POST})
    public DefaultWebApiResult auditOrder(@RequestBody OrderAutoAuditHandlerParaVo reqDto) {
        log.info("处理订单自动客审数据：{}", reqDto.getOrderId());
        return DefaultWebApiResult.success();
    }
}
