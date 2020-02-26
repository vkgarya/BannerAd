package com.coupon.bean.jpa;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.coupon.constants.CouponType;
import com.coupon.constants.Currency;
import com.coupon.partner.constants.Status;
import com.coupon.user.bean.jpa.PartnerEntity;
import com.coupon.utils.UTCDateTimeConverter;

@Entity
@Table(name = "coupon")
public class CouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "limit_per_user")
    private Integer limitPerUser;

    @Column(name = "coupon_type")
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "is_manual")
    private Boolean isManual;

    @Column(name = "min_cart_value")
    private Double minCartValue;

    @Column(name = "max_discount")
    private Double maxDiscount;

    @Column(name = "coupon_limit")
    private Integer couponLimit;

    @Column(name = "coupon_limit_amount")
    private Double couponLimitAmount;

    @Column(name = "is_mergeable")
    private Boolean isMergeable;

    @Column(name = "referral_code_required")
    private Boolean referralCodeRequired;

    @Column(name = "partner_id_ref")
    private Integer partnerId;

    @Column(name="created_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdOn;

    @Column(name="updated_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime updatedOn;

    @Column(name="started_on", nullable = false)
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime startedOn;

    @Column(name="closed_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime closedOn;

    @Column(name="status")
    private String status = Status.INACTIVE;

    @OneToMany(mappedBy = "couponEntity", targetEntity = CouponProductRestrictionsEntity.class)
    private List<CouponProductRestrictionsEntity> couponProductRestrictionsEntities;

    @OneToMany(mappedBy = "couponEntity", targetEntity = RuleCalendarMappingEntity.class)
    private List<RuleCalendarMappingEntity> ruleCalendarMappingEntities;

    @OneToMany(mappedBy = "couponEntity", targetEntity = RuleCategoryMappingEntity.class)
    private List<RuleCategoryMappingEntity> ruleCategoryMappingEntities;

    @OneToMany(mappedBy = "couponEntity", targetEntity = RuleOfferMappingEntity.class)
    private List<RuleOfferMappingEntity> ruleOfferMappingEntities;

    @OneToMany(mappedBy = "couponEntity", targetEntity = RuleTransactionMappingEntity.class)
    private List<RuleTransactionMappingEntity> ruleTransactionMappingEntities;

    @OneToMany(mappedBy = "couponEntity", targetEntity = CouponCodeLanguageMappingEntity.class)
    private List<CouponCodeLanguageMappingEntity> listOfCouponCodes;

    @OneToMany(mappedBy = "couponEntity", targetEntity = CouponDescriptionLanguageMappingEntity.class)
    private List<CouponDescriptionLanguageMappingEntity> listOfCouponDesc;

    public CouponEntity() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public CouponType getCouponType() {
        return couponType;
    }

    public void setCouponType(CouponType couponType) {
        this.couponType = couponType;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Boolean getManual() {
        if (isManual == null) {
            return true;
        }

        return isManual;
    }

    public void setManual(Boolean manual) {
        isManual = manual;
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

    public Boolean getMergeable() {
        if (isMergeable == null) {
            return false;
        }

        return isMergeable;
    }

    public void setMergeable(Boolean mergeable) {
        isMergeable = mergeable;
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

    public ZonedDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(ZonedDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ZonedDateTime getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(ZonedDateTime startedOn) {
        this.startedOn = startedOn;
    }

    public ZonedDateTime getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(ZonedDateTime closedOn) {
        this.closedOn = closedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(Integer couponLimit) {
        this.couponLimit = couponLimit;
    }

    public Double getCouponLimitAmount() {
        return couponLimitAmount;
    }

    public void setCouponLimitAmount(Double couponLimitAmount) {
        this.couponLimitAmount = couponLimitAmount;
    }

    public List<CouponCodeLanguageMappingEntity> getListOfCouponCodes() {
        return listOfCouponCodes;
    }

    public List<CouponProductRestrictionsEntity> getCouponProductRestrictionsEntities() {
        return couponProductRestrictionsEntities;
    }

    public void setCouponProductRestrictionsEntities(List<CouponProductRestrictionsEntity> couponProductRestrictionsEntities) {
        this.couponProductRestrictionsEntities = couponProductRestrictionsEntities;
    }

    public List<RuleCalendarMappingEntity> getRuleCalendarMappingEntities() {
        return ruleCalendarMappingEntities;
    }

    public void setRuleCalendarMappingEntities(List<RuleCalendarMappingEntity> ruleCalendarMappingEntities) {
        this.ruleCalendarMappingEntities = ruleCalendarMappingEntities;
    }

    public List<RuleCategoryMappingEntity> getRuleCategoryMappingEntities() {
        return ruleCategoryMappingEntities;
    }

    public void setRuleCategoryMappingEntities(List<RuleCategoryMappingEntity> ruleCategoryMappingEntities) {
        this.ruleCategoryMappingEntities = ruleCategoryMappingEntities;
    }

    public List<RuleOfferMappingEntity> getRuleOfferMappingEntities() {
        return ruleOfferMappingEntities;
    }

    public void setRuleOfferMappingEntities(List<RuleOfferMappingEntity> ruleOfferMappingEntities) {
        this.ruleOfferMappingEntities = ruleOfferMappingEntities;
    }

    public List<RuleTransactionMappingEntity> getRuleTransactionMappingEntities() {
        return ruleTransactionMappingEntities;
    }

    public void setRuleTransactionMappingEntities(List<RuleTransactionMappingEntity> ruleTransactionMappingEntities) {
        this.ruleTransactionMappingEntities = ruleTransactionMappingEntities;
    }

    public void setListOfCouponCodes(List<CouponCodeLanguageMappingEntity> listOfCouponCodes) {
        this.listOfCouponCodes = listOfCouponCodes;
    }

    public List<CouponDescriptionLanguageMappingEntity> getListOfCouponDesc() {
        return listOfCouponDesc;
    }

    public void setListOfCouponDesc(List<CouponDescriptionLanguageMappingEntity> listOfCouponDesc) {
        this.listOfCouponDesc = listOfCouponDesc;
    }

    public Integer getLimitPerUser() {
        return limitPerUser;
    }

    public void setLimitPerUser(Integer limitPerUser) {
        this.limitPerUser = limitPerUser;
    }

    public Boolean getReferralCodeRequired() {
        return referralCodeRequired;
    }

    public void setReferralCodeRequired(Boolean referralCodeRequired) {
        this.referralCodeRequired = referralCodeRequired;
    }

    public Double getMinCartValueToShowCoupon() {
        if (this.minCartValue == null) {
            return 0.0;
        }

        return this.minCartValue * 0.2;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }
}
