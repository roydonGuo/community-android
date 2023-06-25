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

}