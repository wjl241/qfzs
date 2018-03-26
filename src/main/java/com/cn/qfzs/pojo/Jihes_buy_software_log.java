package com.cn.qfzs.pojo;

public class Jihes_buy_software_log {
    private Integer id;

    private Integer userId;

    private Byte softwareId;

    private Integer startTime;

    private Integer endTime;

    private Byte useCard;

    private Integer cardId;

    private Integer createTime;

    private Integer updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Byte softwareId) {
        this.softwareId = softwareId;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Byte getUseCard() {
        return useCard;
    }

    public void setUseCard(Byte useCard) {
        this.useCard = useCard;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
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
}