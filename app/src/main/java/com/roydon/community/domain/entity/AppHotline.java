package com.roydon.community.domain.entity;

import java.util.Date;
import java.util.Map;

/**
 * 紧急热线对象 app_hotline
 *
 * @author roydon
 * @date 2023-08-07
 */
public class AppHotline extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 热线id
     */
    private Long hotlineId;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    public AppHotline() {
    }

    public AppHotline(Long hotlineId, String leader, String telephone, String status) {
        this.hotlineId = hotlineId;
        this.leader = leader;
        this.telephone = telephone;
        this.status = status;
    }

    public AppHotline(String searchValue, String createBy, Date createTime, String updateBy, Date updateTime, String remark, Map<String, Object> params, Long hotlineId, String leader, String telephone, String status) {
        super(searchValue, createBy, createTime, updateBy, updateTime, remark, params);
        this.hotlineId = hotlineId;
        this.leader = leader;
        this.telephone = telephone;
        this.status = status;
    }

    public Long getHotlineId() {
        return hotlineId;
    }

    public void setHotlineId(Long hotlineId) {
        this.hotlineId = hotlineId;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
