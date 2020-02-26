package com.coupon.bean;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.coupon.constants.CalenderType;
import com.coupon.constants.Relation;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleCalendarDTO implements Serializable {
    @NotBlank
    @JsonProperty("cal_type")
    private CalenderType calType;

    @JsonProperty("type_from")
    private String typeFrom;

    @JsonProperty("type_to")
    private String typeTo;

    @JsonProperty("limit_per_user")
    private Integer limitPerUser;

    @JsonProperty("coupon_limit")
    private Integer couponLimit;

    private Relation relation;

    public CalenderType getCalType() {
        return calType;
    }

    public void setCalType(CalenderType calType) {
        this.calType = calType;
    }

    public String getTypeFrom() {
        return typeFrom;
    }

    public void setTypeFrom(String typeFrom) {
        this.typeFrom = typeFrom;
    }

    public String getTypeTo() {
        return typeTo;
    }

    public void setTypeTo(String typeTo) {
        this.typeTo = typeTo;
    }

    public Integer getLimitPerUser() {
        return limitPerUser;
    }

    public void setLimitPerUser(Integer limitPerUser) {
        this.limitPerUser = limitPerUser;
    }

    public Integer getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(Integer couponLimit) {
        this.couponLimit = couponLimit;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }
}
