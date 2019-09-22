package com.cjyfff.ofc.common;

import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OfcTaskLog {
    public static void info(String str, Object... obj) {
        log.info(str, obj);
        XxlJobLogger.log("[ INFO ]" + str, obj);
    }

    public static void warn(String str, Object... obj) {
        log.warn(str, obj);
        XxlJobLogger.log("[ WARN ]" + str, obj);
    }

    public static void error(String str, Object... obj) {
        log.error(str, obj);
        XxlJobLogger.log("[ ERROR ]" + str, obj);
    }
}
