package com.roydon.community.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class MallUserAddress implements Serializable {
    private static final long serialVersionUID = 187079772473647163L;

    private String addressId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 省代码
     */
    private String provinceCode;
    /**
     * 市代码
     */
    private String cityCode;
    /**
     * 区县代码
     */
    private String regionCode;
    /**
     * 社区id
     */
    private Long communityId;
    /**
     * 备注
     */
    private String nickname;
    /**
     * 号码
     */
    private String telephone;
    /**
     * 详细地址
     */
    private String completeAddress;
    /**
     * 状态：0；1默认收货地址；
     */
    private String isDefault;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String remark;

    public MallUserAddress() {
    }

    public MallUserAddress(String addressId, Long userId, String provinceCode, String cityCode, String regionCode, Long communityId, String nickname, String telephone, String completeAddress, String isDefault, Date createTime, String createBy, Date updateTime, String updateBy, String remark) {
        this.addressId = addressId;
        this.userId = userId;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.regionCode = regionCode;
        this.communityId = communityId;
        this.nickname = nickname;
        this.telephone = telephone;
        this.completeAddress = completeAddress;
        this.isDefault = isDefault;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.remark = remark;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
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
