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

import com.coupon.bean.CouponUserRestrictionDTO;
import com.coupon.constants.RestrictionType;

@Entity
@Table(name = "coupon_user_restrictions")
public class CouponUserRestrictionsEntity  implements Serializable {
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

    @Column(name = "emails", nullable = false)
    private String emails;

    public CouponUserRestrictionsEntity () {
        super();
    }

    public CouponUserRestrictionsEntity(CouponUserRestrictionDTO couponProductRestrictionDTO) {
        super();
        this.setEmails(couponProductRestrictionDTO.getEmails());
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

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }
}
