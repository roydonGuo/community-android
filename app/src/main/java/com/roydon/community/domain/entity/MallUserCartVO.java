package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author roydon
 * @date 2023/6/25 3:35
 * @description community-android
 */
public class MallUserCartVO implements Serializable {

    private static final long serialVersionUID = -71659594271224095L;

    private String cartId;

    private Long userId;
    /**
     * 商品id
     */
    private String goodsId;
    private String goodsTitle;
    private Double goodsPrice;
    private String goodsImg;
    /**
     * 数量
     */
    private Integer goodsCount;
    /**
     * 选择状态0未选1已选
     */
    private String defaultActive;

    private Date createTime;

    public MallUserCartVO() {
    }

    public MallUserCartVO(String cartId, Long userId, String goodsId, String goodsTitle, Double goodsPrice, String goodsImg, Integer goodsCount, String defaultActive, Date createTime) {
        this.cartId = cartId;
        this.userId = userId;
        this.goodsId = goodsId;
        this.goodsTitle = goodsTitle;
        this.goodsPrice = goodsPrice;
        this.goodsImg = goodsImg;
        this.goodsCount = goodsCount;
        this.defaultActive = defaultActive;
        this.createTime = createTime;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getDefaultActive() {
        return defaultActive;
    }

    public void setDefaultActive(String defaultActive) {
        this.defaultActive = defaultActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
