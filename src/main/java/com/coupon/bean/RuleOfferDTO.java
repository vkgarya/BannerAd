package com.coupon.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleOfferDTO implements Serializable {
    @JsonProperty("buy_skus")
    private String buySkus;

    @JsonProperty("offer_skus")
    private String offerSkus;

    @JsonProperty("buy_quantity")
    private Integer buyQuantity;

    @JsonProperty("offer_quantity")
    private Integer offerQuantity;

    @JsonProperty("offer_description")
    private String offerDescription;

    @JsonProperty("is_multiple")
    private Boolean isMultiple = false;

    public String getBuySkus() {
        return buySkus;
    }

    public void setBuySkus(String buySkus) {
        this.buySkus = buySkus;
    }

    public String getOfferSkus() {
        return offerSkus;
    }

    public void setOfferSkus(String offerSkus) {
        this.offerSkus = offerSkus;
    }

    public Integer getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Integer buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Integer getOfferQuantity() {
        return offerQuantity;
    }

    public void setOfferQuantity(Integer offerQuantity) {
        this.offerQuantity = offerQuantity;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public Boolean getIsMultiple() {
        return isMultiple;
    }

    public void setIsMultiple(Boolean isMultiple) {
        this.isMultiple = isMultiple;
    }
}
