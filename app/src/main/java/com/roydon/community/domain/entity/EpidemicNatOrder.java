package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约核酸检测NAT对象 epidemic_nat_order
 *
 * @author roydon
 * @date 2023-08-23
 */
public class EpidemicNatOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 预约id
     */
    private Long orderId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 预约时间
     */
    private Date orderTime;

    /**
     * 预约状态(0已预约，1已完成，2已取消)
     */
    private String orderStatus;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public EpidemicNatOrder() {
    }

    public EpidemicNatOrder(Long orderId, Long userId, String username, String realName, String telephone, String idCard, Date orderTime, String orderStatus, Date createTime, String createBy, Date updateTime, String updateBy, String remark) {
        this.orderId = orderId;
        this.userId = userId;
        this.username = username;
        this.realName = realName;
        this.telephone = telephone;
        this.idCard = idCard;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.remark = remark;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
}
