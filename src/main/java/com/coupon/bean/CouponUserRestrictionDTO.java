package com.coupon.bean;

import java.io.Serializable;

import com.coupon.constants.RestrictionType;

public class CouponUserRestrictionDTO implements Serializable {
    private RestrictionType type;

    private String emails;

    public RestrictionType getType() {
        return type;
    }

    public void setType(RestrictionType type) {
        this.type = type;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}
