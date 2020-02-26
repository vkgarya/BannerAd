package com.coupon.bean;

import java.io.Serializable;

import com.coupon.constants.RestrictionType;

public class CouponProductRestrictionDTO implements Serializable {
    private RestrictionType type;

    private String skus;

    public RestrictionType getType() {
        return type;
    }

    public void setType(RestrictionType type) {
        this.type = type;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }
}
