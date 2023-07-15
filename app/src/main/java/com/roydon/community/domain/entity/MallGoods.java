package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class MallGoods implements Serializable {
    private static final long serialVersionUID = -38678073295414031L;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 标题
     */
    private String goodsTitle;
    /**
     * 图片
     */
    private String goodsImg;
    /**
     * 商品描述
     */
    private String goodsDetails;
    /**
     * 商品价格
     */
    private Double goodsPrice;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 社区id
     */
    private Long deptId;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 是否下架0正常1下架
     */
    private String status;

    /**
     * 浏览数
     */
    private Integer viewNum;
    /**
     * 创建时间
     */
    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public MallGoods() {
    }

    public MallGoods(String goodsId, String goodsTitle, String goodsImg, String goodsDetails, Double goodsPrice, Long userId, Long deptId, Integer stock, String status, Integer viewNum, Date createTime, String createBy, Date updateTime, String updateBy, String remark) {
        this.goodsId = goodsId;
        this.goodsTitle = goodsTitle;
        this.goodsImg = goodsImg;
        this.goodsDetails = goodsDetails;
        this.goodsPrice = goodsPrice;
        this.userId = userId;
        this.deptId = deptId;
        this.stock = stock;
        this.status = status;
        this.viewNum = viewNum;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.remark = remark;
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

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDetails() {
        return goodsDetails;
    }

    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
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
