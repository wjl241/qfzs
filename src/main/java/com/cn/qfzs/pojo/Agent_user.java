package com.cn.qfzs.pojo;

import java.math.BigDecimal;

public class Agent_user {
    private Integer id;

    private Byte level;

    private Integer parentUserId;

    private Integer tmUserId;

    private Integer loginTime;

    private Boolean isdelete;

    private Integer createTime;

    private Integer updateTime;

    private Integer childCount;

    private Integer childCount1;

    private Integer childCount2;

    private Float commissionPercent;

    private BigDecimal commission;

    private BigDecimal commissionFreeze;

    private String phone;

    private String password;

    private String userName;

    private String alipayId;

    private String alipayName;

    private Integer pid1;

    private Integer pid2;

    private String cookie;

    private String memberId;

    private Integer buyMm;

    private Integer agentType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Integer getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Integer parentUserId) {
        this.parentUserId = parentUserId;
    }

    public Integer getTmUserId() {
        return tmUserId;
    }

    public void setTmUserId(Integer tmUserId) {
        this.tmUserId = tmUserId;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public Integer getChildCount1() {
        return childCount1;
    }

    public void setChildCount1(Integer childCount1) {
        this.childCount1 = childCount1;
    }

    public Integer getChildCount2() {
        return childCount2;
    }

    public void setChildCount2(Integer childCount2) {
        this.childCount2 = childCount2;
    }

    public Float getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(Float commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getCommissionFreeze() {
        return commissionFreeze;
    }

    public void setCommissionFreeze(BigDecimal commissionFreeze) {
        this.commissionFreeze = commissionFreeze;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getAlipayId() {
        return alipayId;
    }

    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId == null ? null : alipayId.trim();
    }

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName == null ? null : alipayName.trim();
    }

    public Integer getPid1() {
        return pid1;
    }

    public void setPid1(Integer pid1) {
        this.pid1 = pid1;
    }

    public Integer getPid2() {
        return pid2;
    }

    public void setPid2(Integer pid2) {
        this.pid2 = pid2;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie == null ? null : cookie.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public Integer getBuyMm() {
        return buyMm;
    }

    public void setBuyMm(Integer buyMm) {
        this.buyMm = buyMm;
    }

    public Integer getAgentType() {
        return agentType;
    }

    public void setAgentType(Integer agentType) {
        this.agentType = agentType;
    }
}