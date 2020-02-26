package com.coupon.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coupon.bean.jpa.ReferralBonusMappingEntity;
import com.coupon.bean.jpa.ReferralCouponCodeLanguageMappingEntity;
import com.coupon.bean.jpa.ReferralCouponDescriptionLanguageMappingEntity;
import com.coupon.bean.jpa.ReferralCouponEntity;
import com.coupon.constants.ReferralCouponType;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Referral implements Serializable {
    private Double amount;
    private Map<String, String> code = new HashMap<>();
    private Map<String, String> description = new HashMap<>();
    @JsonIgnore
    private Integer couponId;

    public Referral(ReferralBonusMappingEntity entity, CartRequest cartRequest) {
        super();
        ReferralCouponEntity referralCouponEntity = entity.getReferralCouponEntity();
        List<ReferralCouponCodeLanguageMappingEntity> codes = referralCouponEntity.getListOfCouponCodes();
        List<ReferralCouponDescriptionLanguageMappingEntity> descs = referralCouponEntity.getListOfCouponDesc();

        for (ReferralCouponCodeLanguageMappingEntity code : codes) {
            this.code.put(code.getLanguage().name(), code.getCouponCode());
        }

        for (ReferralCouponDescriptionLanguageMappingEntity desc : descs) {
            this.description.put(desc.getLanguage().name(), desc.getCouponDesc());
        }

        if (entity.getBonusType().equals(ReferralCouponType.flat)) {
            this.setAmount(entity.getBonusValue());
        } else if (entity.getBonusType().equals(ReferralCouponType.percentage)) {
            this.setAmount(Math.min(entity.getBonusValue() * cartRequest.getTotalCartValue() / 100, entity.getMaxDiscount()));
        }

        this.couponId = entity.getId();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }
}
