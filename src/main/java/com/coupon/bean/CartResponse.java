package com.coupon.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartResponse implements Serializable {
    private String status;
    private String message;
    private Integer status_code;
    private String txn_id;
    private List<Coupon> coupons = new ArrayList<>();
    private List<Coupon> discounts = new ArrayList<>();
    private List<Referral> referrals = new ArrayList<>();
    private Double referral_bonus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public List<Coupon> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Coupon> discounts) {
        this.discounts = discounts;
    }

    public List<Referral> getReferrals() {
        return referrals;
    }

    public void setReferrals(List<Referral> referrals) {
        this.referrals = referrals;
    }

    public Double getReferral_bonus() {
        return referral_bonus;
    }

    public void setReferral_bonus(Double referral_bonus) {
        this.referral_bonus = referral_bonus;
    }

    public void setCouponsAndDiscounts(List<Coupon> coupons) {
        Double maxDiscount = 0.0;
        Coupon maxDiscountCoupon = null;

        for (Coupon coupon : coupons) {
            if (!coupon.getIs_manual() && !coupon.getDisabled() && coupon.getAmount() != null && maxDiscount < coupon.getAmount()) {
                maxDiscount = coupon.getAmount();
                maxDiscountCoupon = coupon;
            }
        }

        for (Coupon coupon : coupons) {
            if (coupon.getIs_manual() || (maxDiscountCoupon == null && coupon.getDisabled() && coupon.getMax_discount() != null && coupon.getMax_discount() > maxDiscount)) {
                this.coupons.add(coupon);
            }
        }

        if (maxDiscountCoupon != null) {
            this.discounts.add(maxDiscountCoupon);
        }
    }
}
