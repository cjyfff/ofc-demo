package com.cjyfff.ofc.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 只打印信息，不抛出异常
 */
@Slf4j
public class OrderExecutorRejectPolicy implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.info("线程池任务：{} 被拒绝", r.toString());
    }
}
