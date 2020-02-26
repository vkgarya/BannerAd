package com.coupon.bean.jpa;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coupon.constants.ReferralCouponType;
import com.coupon.constants.ReferralUserType;
import com.coupon.utils.UTCDateTimeConverter;

@Entity
@Table(name = "referral_bonus_mapping")
public class ReferralBonusMappingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ref_id", referencedColumnName = "id")
    private ReferralCouponEntity referralCouponEntity;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private ReferralUserType userType;

    @Column(name = "bonus_type")
    @Enumerated(EnumType.STRING)
    private ReferralCouponType bonusType;

    @Column(name = "bonus_value", nullable = false)
    private Double bonusValue;

    @Column(name = "min_cart_value")
    private Double minCartValue;

    @Column(name = "max_discount")
    private Double maxDiscount;

    @Column(name="created_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReferralCouponEntity getReferralCouponEntity() {
        return referralCouponEntity;
    }

    public void setReferralCouponEntity(ReferralCouponEntity referralCouponEntity) {
        this.referralCouponEntity = referralCouponEntity;
    }

    public ReferralUserType getUserType() {
        return userType;
    }

    public void setUserType(ReferralUserType userType) {
        this.userType = userType;
    }

    public ReferralCouponType getBonusType() {
        return bonusType;
    }

    public void setBonusType(ReferralCouponType bonusType) {
        this.bonusType = bonusType;
    }

    public Double getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(Double bonusValue) {
        this.bonusValue = bonusValue;
    }

    public Double getMinCartValue() {
        return minCartValue;
    }

    public void setMinCartValue(Double minCartValue) {
        this.minCartValue = minCartValue;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
