package com.roydon.community.domain.vo;

import com.roydon.community.domain.entity.BaseEntity;
import com.roydon.community.domain.entity.SysDept;

import java.util.Date;
import java.util.Map;

/**
 * project : community-server
 * <p> 返回给app端的用户信息 </p>
 *
 * @author roydon
 * @date 2023-07-18【星期二】
 **/
public class AppUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long userId;

    /**
     * 单元ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 用户性别
     */
    private String sex;

    private Integer age;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    private String isTenant;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 单元对象
     */
    private SysDept dept;

    public AppUser() {
    }

    public AppUser(String searchValue, String createBy, Date createTime, String updateBy, Date updateTime, String remark, Map<String, Object> params) {
        super(searchValue, createBy, createTime, updateBy, updateTime, remark, params);
    }

    public AppUser(Long userId, Long deptId, String userName, String nickName, String email, String phonenumber, String realName, String idCard, String sex, Integer age, String avatar, String status, String isTenant, String delFlag, String loginIp, Date loginDate, SysDept dept) {
        this.userId = userId;
        this.deptId = deptId;
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.phonenumber = phonenumber;
        this.realName = realName;
        this.idCard = idCard;
        this.sex = sex;
        this.age = age;
        this.avatar = avatar;
        this.status = status;
        this.isTenant = isTenant;
        this.delFlag = delFlag;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
        this.dept = dept;
    }

    public AppUser(String searchValue, String createBy, Date createTime, String updateBy, Date updateTime, String remark, Map<String, Object> params, Long userId, Long deptId, String userName, String nickName, String email, String phonenumber, String realName, String idCard, String sex, Integer age, String avatar, String status, String isTenant, String delFlag, String loginIp, Date loginDate, SysDept dept) {
        super(searchValue, createBy, createTime, updateBy, updateTime, remark, params);
        this.userId = userId;
        this.deptId = deptId;
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.phonenumber = phonenumber;
        this.realName = realName;
        this.idCard = idCard;
        this.sex = sex;
        this.age = age;
        this.avatar = avatar;
        this.status = status;
        this.isTenant = isTenant;
        this.delFlag = delFlag;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
        this.dept = dept;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsTenant() {
        return isTenant;
    }

    public void setIsTenant(String isTenant) {
        this.isTenant = isTenant;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }
}

