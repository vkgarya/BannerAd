package com.coupon.bean.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coupon.bean.CouponProductRestrictionDTO;
import com.coupon.constants.RestrictionType;

@Entity
@Table(name = "coupon_product_restrictions")
public class CouponProductRestrictionsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id_ref", referencedColumnName = "id")
    private CouponEntity couponEntity;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RestrictionType type;

    @Column(name = "skus", nullable = false)
    private String skus;

    public CouponProductRestrictionsEntity () {
        super();
    }

    public CouponProductRestrictionsEntity(CouponProductRestrictionDTO couponProductRestrictionDTO) {
        super();
        this.setSkus(couponProductRestrictionDTO.getSkus());
        this.setType(couponProductRestrictionDTO.getType());
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

    public RestrictionType getType() {
        return type;
    }

    public void setType(RestrictionType type) {
        this.type = type;
    }

    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }
}
