package com.cjyfff.ofc.common.enums;

/**
 * Created by jiashen on 2020/12/27.
 */
public enum OrderHandleStatus {
    FINISH(0, "处理完成"),
    PROCESSING(1, "处理中");

    private Integer status;

    private String desc;

    OrderHandleStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
