package com.cjyfff.ofc.common.enums;

/**
 * Created by jiashen on 19-9-20.
 */
public enum OrderStatus {
    NEW(300, "新订单"),
    INIT(410, "初始化"),
    AUDITED(500, "审核通过"),
    STOCK(600, "库房分配完成"),
    SENDWMS(610, "物流商分配完成");

    private Integer status;

    private String desc;

    OrderStatus(Integer status, String desc) {
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
