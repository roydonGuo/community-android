package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class MallOrder implements Serializable {
    private static final long serialVersionUID = -21599664428036113L;

    private String orderId;

    private String userId;
    private String userName;
    /**
     * 收货地址id
     */
    private String addressId;
    /**
     * 实际付款总价
     */
    private Double totalPrice;
    /**
     * 0未付款1已付款
     */
    private String payStatus;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public MallOrder() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public MallOrder(String orderId, String userId, String userName, String addressId, Double totalPrice, String payStatus, Date createTime, String createBy, Date updateTime, String updateBy, String remark) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.addressId = addressId;
        this.totalPrice = totalPrice;
        this.payStatus = payStatus;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.remark = remark;
    }
}