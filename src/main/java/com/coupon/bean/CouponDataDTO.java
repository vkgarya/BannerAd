package com.coupon.bean;

import java.time.ZonedDateTime;
import java.util.*;

import javax.validation.constraints.NotBlank;

import com.coupon.constants.CouponType;
import com.coupon.constants.Currency;
import com.coupon.constants.Language;
import com.coupon.user.bean.Partner;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CouponDataDTO {
    private Integer id;
    @NotBlank
    private Currency currency;

    private String status;

    @NotBlank
    @JsonProperty("coupon_type")
    private CouponType couponType;

    @JsonProperty("limit_per_user")
    private Integer limitPerUser;

    @JsonProperty("coupon_limit")
    private Integer couponLimit;

    @JsonProperty("coupon_limit_amount")
    private Double couponLimitAmount;

    @JsonProperty("discount_percentage")
    private Double discountPercentage;

    @JsonProperty("is_manual")
    private Boolean manual = false;

    @JsonProperty("min_cart_value")
    private Double minCartValue = 0.0;

    @JsonProperty("max_discount")
    private Double maxDiscount;

    @JsonProperty("is_mergeable")
    private Boolean mergeable;

    @JsonProperty("referral_code_required")
    private Boolean referralCodeRequired;

    private List<LangValueDTO> codes;
    private List<LangValueDTO> descriptions;

    private Partner partner;

    @JsonProperty("partner_id")
    private Integer partnerId;

    @JsonProperty("calendar_rules")
    private List<RuleCalendarDTO> calendarRules = new ArrayList<>();
    @JsonProperty("category_rules")
    private List<RuleCategoryDTO> categoryRules = new ArrayList<>();
    @JsonProperty("offer_rules")
    private List<RuleOfferDTO> offerRules = new ArrayList<>();
    @JsonProperty("transaction_rules")
    private List<RuleTransactionDTO> transactionRules = new ArrayList<>();
    @JsonProperty("event_rules")
    private List<RuleEventDTO> eventRules = new ArrayList<>();
    private List<CouponFileDTO> files = new ArrayList<>();

    @JsonProperty("created_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime createdOn;
    @JsonProperty("started_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime startedOn;
    @JsonProperty("closed_on")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime closedOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getLimitPerUser() {
        return limitPerUser;
    }

    public void setLimitPerUser(Integer limitPerUser) {
        this.limitPerUser = limitPerUser;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Boolean getManual() {
        return manual;
    }

    public void setManual(Boolean manual) {
        this.manual = manual;
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

    public Boolean getMergeable() {
        return mergeable;
    }

    public void setMergeable(Boolean mergeable) {
        this.mergeable = mergeable;
    }

    public Boolean getReferralCodeRequired() {
        return referralCodeRequired;
    }

    public void setReferralCodeRequired(Boolean referralCodeRequired) {
        this.referralCodeRequired = referralCodeRequired;
    }

    public List<RuleCalendarDTO> getCalendarRules() {
        return calendarRules;
    }

    public void setCalendarRules(List<RuleCalendarDTO> calendarRules) {
        this.calendarRules = calendarRules;
    }

    public List<RuleCategoryDTO> getCategoryRules() {
        return categoryRules;
    }

    public void setCategoryRules(List<RuleCategoryDTO> categoryRules) {
        this.categoryRules = categoryRules;
    }

    public List<RuleOfferDTO> getOfferRules() {
        return offerRules;
    }

    public void setOfferRules(List<RuleOfferDTO> offerRules) {
        this.offerRules = offerRules;
    }

    public List<RuleTransactionDTO> getTransactionRules() {
        return transactionRules;
    }

    public void setTransactionRules(List<RuleTransactionDTO> transactionRules) {
        this.transactionRules = transactionRules;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
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

    public List<RuleEventDTO> getEventRules() {
        return eventRules;
    }

    public void setEventRules(List<RuleEventDTO> eventRules) {
        this.eventRules = eventRules;
    }

    public List<LangValueDTO> getCodes() {
        return codes;
    }

    public void setCodes(List<LangValueDTO> codes) {
        this.codes = codes;
    }

    public List<LangValueDTO> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<LangValueDTO> descriptions) {
        this.descriptions = descriptions;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public List<CouponFileDTO> getFiles() {
        return files;
    }

    public void setFiles(List<CouponFileDTO> files) {
        this.files = files;
    }
}
