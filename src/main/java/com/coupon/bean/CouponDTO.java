package com.coupon.bean;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import com.coupon.bean.jpa.CouponEntity;
import com.coupon.constants.CouponType;
import com.coupon.constants.Language;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CouponDTO {
    private Integer id;

    private Double discount_percentage;

    private Boolean is_manual = false;

    private CouponType coupon_type;

    private Double min_cart_value = 0.0;

    private Double max_discount;

    private List<LangValueDTO> codes;

    private List<LangValueDTO> descriptions;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime started_on;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime closed_on;

    public CouponDTO() {
        super();
    }

    public CouponDTO(CouponEntity entity) {
        this.closed_on = entity.getClosedOn();
        this.started_on = entity.getStartedOn();
        this.coupon_type = entity.getCouponType();
        this.discount_percentage = entity.getDiscountPercentage();
        this.id = entity.getId();
        this.is_manual = entity.getManual();
        this.max_discount = entity.getMaxDiscount();
        this.min_cart_value = entity.getMinCartValue();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(Double discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public Boolean getIs_manual() {
        return is_manual;
    }

    public void setIs_manual(Boolean is_manual) {
        this.is_manual = is_manual;
    }

    public CouponType getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(CouponType coupon_type) {
        this.coupon_type = coupon_type;
    }

    public Double getMin_cart_value() {
        return min_cart_value;
    }

    public void setMin_cart_value(Double min_cart_value) {
        this.min_cart_value = min_cart_value;
    }

    public Double getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(Double max_discount) {
        this.max_discount = max_discount;
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

    public ZonedDateTime getStarted_on() {
        return started_on;
    }

    public void setStarted_on(ZonedDateTime started_on) {
        this.started_on = started_on;
    }

    public ZonedDateTime getClosed_on() {
        return closed_on;
    }

    public void setClosed_on(ZonedDateTime closed_on) {
        this.closed_on = closed_on;
    }
}
