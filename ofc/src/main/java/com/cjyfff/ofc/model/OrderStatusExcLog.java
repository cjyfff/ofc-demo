package com.cjyfff.ofc.model;

import java.util.Date;

public class OrderStatusExcLog {
    private Long id;

    private String orderId;

    private Integer status;

    private Integer statusExcTime;

    private Date createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatusExcTime() {
        return statusExcTime;
    }

    public void setStatusExcTime(Integer statusExcTime) {
        this.statusExcTime = statusExcTime;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}