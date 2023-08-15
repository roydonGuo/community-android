package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 疫苗接种记录对象 epidemic_inoculation_history
 *
 * @author roydon
 * @date 2023-08-15
 */
public class EpidemicInoculationHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long historyId;

    private Long userId;

    private String username;

    private String realName;

    private String telephone;

    private String idCard;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public EpidemicInoculationHistory() {
    }

    public EpidemicInoculationHistory(Long historyId, Long userId, String username, String realName, String telephone, String idCard, Date createTime, String createBy, Date updateTime, String updateBy, String remark) {
        this.historyId = historyId;
        this.userId = userId;
        this.username = username;
        this.realName = realName;
        this.telephone = telephone;
        this.idCard = idCard;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.remark = remark;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
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
