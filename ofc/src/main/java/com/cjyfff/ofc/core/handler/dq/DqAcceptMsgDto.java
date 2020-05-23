package com.cjyfff.ofc.core.handler.dq;

import lombok.Getter;
import lombok.Setter;


/**
 * @author cjyfff
 * @date 2020/5/23 20:49
 */
@Setter
@Getter
public class DqAcceptMsgDto {

    /**
     * taskId，需要保证唯一
     */
    private String taskId;

    /**
     * 调用方法名
     */
    private String functionName;

    /**
     * 延时时间，单位秒
     */
    private Long delayTime;

    /**
     * 随机字符串，用于保证接口幂等
     * 冪等不能使用 taskId，因为任务可能会由 A 节点转发到 B 节点，幂等使用分布式锁实现的话，
     * 转发过程中 A 还没有对 task id 解锁，B 节点使用 task id 做幂等判断时就会拒绝处理
     * 因此使用另一个随机字符串来作为请求标记，转发时，A 节点会重新生成这个随机字符串
     */
    private String nonceStr;

    /**
     * 调用参数字符串
     */
    private String params;

    /**
     * 重试次数
     */
    private Byte retryCount;

    /**
     * 重试周期，单位秒
     */
    private Integer retryInterval;
}
