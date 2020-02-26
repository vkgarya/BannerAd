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

import com.coupon.user.bean.jpa.UserEntity;

@Entity
@Table(name = "conversion_data_coupon_mapping")
public class ConversionDataCouponMappingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conversion_data_id_ref", referencedColumnName = "id")
    private ConversionDataEntity conversionDataEntity;

    @ManyToOne
    @JoinColumn(name = "coupon_id_ref", referencedColumnName = "id")
    private CouponEntity couponEntity;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "txn_id")
    private String txnId;

    @Column(name = "coupon_amount")
    private Double couponAmount;

    @Column(name="active")
    private Boolean active = true;

    public ConversionDataCouponMappingEntity () {
        super();
    }

    public ConversionDataCouponMappingEntity(ConversionDataEntity conversionDataEntity, CouponEntity couponEntity, String txnId, Double couponAmount, String userId) {
        this.conversionDataEntity = conversionDataEntity;
        this.couponEntity = couponEntity;
        this.txnId = txnId;
        this.couponAmount = couponAmount;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ConversionDataEntity getConversionDataEntity() {
        return conversionDataEntity;
    }

    public void setConversionDataEntity(ConversionDataEntity conversionDataEntity) {
        this.conversionDataEntity = conversionDataEntity;
    }

    public CouponEntity getCouponEntity() {
        return couponEntity;
    }

    public void setCouponEntity(CouponEntity couponEntity) {
        this.couponEntity = couponEntity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }
}
