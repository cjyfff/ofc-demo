package com.cjyfff.ofc.common;

/**
 * Created by jiashen on 2019/9/6.
 */
public class LockKey {
    public static String getWorkFlowKey(String orderId) {
        return "ofcWorkFlow:" + orderId;
    }
}
