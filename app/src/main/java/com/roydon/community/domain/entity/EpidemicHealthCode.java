package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class EpidemicHealthCode implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 健康码id
     */
    private Long codeId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账号
     */
    private String userName;

    /**
     * 健康码base64格式
     */
    private String codeBase64;

    /**
     * 健康码图片格式
     */
    private String codeImage;

    /**
     * 健康码状态（0绿；1黄；2红）
     */
    private String codeStatus;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public EpidemicHealthCode() {
    }

    public EpidemicHealthCode(Long codeId, Long userId, String userName, String codeBase64, String codeImage, String codeStatus, Date createTime, String createBy, Date updateTime, String updateBy, String remark) {
        this.codeId = codeId;
        this.userId = userId;
        this.userName = userName;
        this.codeBase64 = codeBase64;
        this.codeImage = codeImage;
        this.codeStatus = codeStatus;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.remark = remark;
    }

    public Long getCodeId() {
        return codeId;
    }

    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCodeBase64() {
        return codeBase64;
    }

    public void setCodeBase64(String codeBase64) {
        this.codeBase64 = codeBase64;
    }

    public String getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(String codeImage) {
        this.codeImage = codeImage;
    }

    public String getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
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
