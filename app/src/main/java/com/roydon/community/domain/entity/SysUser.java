package com.roydon.community.domain.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户对象 sys_user
 */
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
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
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 是否为租客（0代表房东 1代表租客）
     */
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

    /**
     * 角色对象
     */
    private List<SysRole> roles;

    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;

    /**
     * 角色ID
     */
    private Long roleId;

    public SysUser() {

    }

    public SysUser(Long userId, Long deptId, String userName, String nickName, String email, String phonenumber, String realName, String idCard, String sex, Integer age, String avatar, String password, String status, String isTenant, String delFlag, String loginIp, Date loginDate, SysDept dept, List<SysRole> roles, Long[] roleIds, Long[] postIds, Long roleId) {
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
        this.password = password;
        this.status = status;
        this.isTenant = isTenant;
        this.delFlag = delFlag;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
        this.dept = dept;
        this.roles = roles;
        this.roleIds = roleIds;
        this.postIds = postIds;
        this.roleId = roleId;
    }

    public SysUser(String searchValue, String createBy, Date createTime, String updateBy, Date updateTime, String remark, Map<String, Object> params, Long userId, Long deptId, String userName, String nickName, String email, String phonenumber, String realName, String idCard, String sex, Integer age, String avatar, String password, String status, String isTenant, String delFlag, String loginIp, Date loginDate, SysDept dept, List<SysRole> roles, Long[] roleIds, Long[] postIds, Long roleId) {
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
        this.password = password;
        this.status = status;
        this.isTenant = isTenant;
        this.delFlag = delFlag;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
        this.dept = dept;
        this.roles = roles;
        this.roleIds = roleIds;
        this.postIds = postIds;
        this.roleId = roleId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
