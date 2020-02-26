package com.coupon.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coupon.bean.jpa.CouponCodeLanguageMappingEntity;
import com.coupon.bean.jpa.CouponDescriptionLanguageMappingEntity;
import com.coupon.bean.jpa.CouponEntity;
import com.coupon.constants.CouponType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coupon implements Serializable {
    private Map<String, String> code = new HashMap<>();
    private Map<String, String> description = new HashMap<>();
    private Double amount;
    private Boolean is_mergeable;
    private Boolean is_manual;
    private Boolean disabled = false;
    private String note;
    private String offer_skus;
    private Integer offerCountUsed = 1;
    @JsonProperty("coupon_id")
    private Integer couponId;
    @JsonIgnore
    private Double max_discount;

    public Coupon() {
        super();
    }

    public Coupon (CouponEntity entity, CartRequest cartRequest) {
        super();
        List<CouponCodeLanguageMappingEntity> codes = entity.getListOfCouponCodes();
        List<CouponDescriptionLanguageMappingEntity> descs = entity.getListOfCouponDesc();

        for (CouponCodeLanguageMappingEntity code : codes) {
            this.code.put(code.getLanguage().name(), code.getCouponCode());
        }

        for (CouponDescriptionLanguageMappingEntity desc : descs) {
            this.description.put(desc.getLanguage().name(), desc.getCouponDesc());
        }

        if (entity.getCouponType().equals(CouponType.flat)) {
            this.setAmount(entity.getMaxDiscount());
        } else if (entity.getCouponType().equals(CouponType.percentage)) {
            if (entity.getMaxDiscount() == null) {
                this.setAmount(entity.getDiscountPercentage() * cartRequest.getTotalCartValue() / 100);
            } else {
                this.setAmount(Math.min(entity.getDiscountPercentage() * cartRequest.getTotalCartValue() / 100, entity.getMaxDiscount()));
            }
        }

        this.is_mergeable = entity.getMergeable();
        this.is_manual = entity.getManual();
        this.couponId = entity.getId();
        this.max_discount = entity.getMaxDiscount();
    }

    public Map<String, String> getCode() {
        return code;
    }

    public void setCode(Map<String, String> code) {
        this.code = code;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void addAmount(Double amount) {
        if (this.amount == null) {
            this.amount =0.0;
        }

        this.amount += amount;
    }

    public Boolean getIs_mergeable() {
        return is_mergeable;
    }

    public void setIs_mergeable(Boolean is_mergeable) {
        this.is_mergeable = is_mergeable;
    }

    public Boolean getIs_manual() {
        return is_manual;
    }

    public void setIs_manual(Boolean is_manual) {
        this.is_manual = is_manual;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getOffer_skus() {
        return offer_skus;
    }

    public void setOffer_skus(String offer_skus) {
        this.offer_skus = offer_skus;
    }

    public Double getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(Double max_discount) {
        this.max_discount = max_discount;
    }

    public Integer getOfferCountUsed() {
        return offerCountUsed;
    }

    public void setOfferCountUsed(Integer offerCountUsed) {
        this.offerCountUsed = offerCountUsed;
    }
}
