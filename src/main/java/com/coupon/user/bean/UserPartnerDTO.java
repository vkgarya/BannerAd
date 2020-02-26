package com.coupon.user.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPartnerDTO {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("partner_id")
    private Integer partnerId;

    private String type;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
