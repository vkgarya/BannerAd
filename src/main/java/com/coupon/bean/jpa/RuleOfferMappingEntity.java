package com.coupon.bean.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coupon.bean.RuleOfferDTO;

@Entity
@Table(name = "rule_offer_mapping")
public class RuleOfferMappingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id_ref", referencedColumnName = "id")
    private CouponEntity couponEntity;

    @Column(name = "buy_skus", nullable = false)
    private String buySkus;

    @Column(name = "offer_skus")
    private String offerSkus;

    @Column(name = "buy_quantity")
    private Integer buyQuantity;

    @Column(name = "offer_quantity")
    private Integer offerQuantity;

    @Column(name = "offer_desc", length = 4096)
    private String offerDescription;

    public RuleOfferMappingEntity() {
        super();
    }

    public RuleOfferMappingEntity(RuleOfferDTO ruleOfferDTO) {
        super();
        this.setBuySkus(ruleOfferDTO.getBuySkus());
        this.setBuyQuantity(ruleOfferDTO.getBuyQuantity());
        this.setOfferSkus(ruleOfferDTO.getOfferSkus());
        this.setOfferDescription(ruleOfferDTO.getOfferDescription());
        this.setOfferQuantity(ruleOfferDTO.getOfferQuantity());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CouponEntity getCouponEntity() {
        return couponEntity;
    }

    public void setCouponEntity(CouponEntity couponEntity) {
        this.couponEntity = couponEntity;
    }

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
}
