package com.roydon.community.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 社区表 sys_dept
 */
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long deptId;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 名称
     */
    private String deptName;

    private Long areaCode;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态:0正常,1停用
     */
    private String status;

    /**
     * 是否为房屋：0不是，1是
     */
    private String isHouse;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 父名称
     */
    private String parentName;

    private List<SysDept> children = new ArrayList<SysDept>();

    public SysDept() {
    }

    public SysDept(Long deptId, Long parentId, String ancestors, String deptName, Long areaCode, Integer orderNum, String leader, String phone, String email, String status, String isHouse, String delFlag, String parentName, List<SysDept> children) {
        this.deptId = deptId;
        this.parentId = parentId;
        this.ancestors = ancestors;
        this.deptName = deptName;
        this.areaCode = areaCode;
        this.orderNum = orderNum;
        this.leader = leader;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.isHouse = isHouse;
        this.delFlag = delFlag;
        this.parentName = parentName;
        this.children = children;
    }

    public SysDept(String searchValue, String createBy, Date createTime, String updateBy, Date updateTime, String remark, Map<String, Object> params, Long deptId, Long parentId, String ancestors, String deptName, Long areaCode, Integer orderNum, String leader, String phone, String email, String status, String isHouse, String delFlag, String parentName, List<SysDept> children) {
        super(searchValue, createBy, createTime, updateBy, updateTime, remark, params);
        this.deptId = deptId;
        this.parentId = parentId;
        this.ancestors = ancestors;
        this.deptName = deptName;
        this.areaCode = areaCode;
        this.orderNum = orderNum;
        this.leader = leader;
        this.phone = phone;
        this.email = email;
        this.status = status;
        this.isHouse = isHouse;
        this.delFlag = delFlag;
        this.parentName = parentName;
        this.children = children;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsHouse() {
        return isHouse;
    }

    public void setIsHouse(String isHouse) {
        this.isHouse = isHouse;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<SysDept> getChildren() {
        return children;
    }

    public void setChildren(List<SysDept> children) {
        this.children = children;
    }

}
