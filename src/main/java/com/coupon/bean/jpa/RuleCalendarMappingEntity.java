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

import com.coupon.bean.RuleCalendarDTO;
import com.coupon.constants.CalenderType;
import com.coupon.constants.Relation;

@Entity
@Table(name = "rule_calendar_mapping")
public class RuleCalendarMappingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id_ref", referencedColumnName = "id")
    private CouponEntity couponEntity;

    @Column(name = "cal_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CalenderType calType;

    @Column(name = "type_from")
    private String typeFrom;

    @Column(name = "type_to")
    private String typeTo;

    @Column(name = "limit_per_user")
    private Integer limitPerUser;

    @Column(name = "coupon_limit")
    private Integer couponLimit;

    @Column(name = "relation")
    @Enumerated(EnumType.STRING)
    private Relation relation;

    public RuleCalendarMappingEntity () {
        super();
    }

    public RuleCalendarMappingEntity(RuleCalendarDTO ruleCalendarDTO) {
        super();
        this.setCalType(ruleCalendarDTO.getCalType());
        this.setCouponLimit(ruleCalendarDTO.getCouponLimit());
        this.setLimitPerUser(ruleCalendarDTO.getLimitPerUser());
        this.setRelation(ruleCalendarDTO.getRelation());
        this.setTypeFrom(ruleCalendarDTO.getTypeFrom());
        this.setTypeTo(ruleCalendarDTO.getTypeTo());
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

    public CalenderType getCalType() {
        return calType;
    }

    public void setCalType(CalenderType calType) {
        this.calType = calType;
    }

    public String getTypeFrom() {
        return typeFrom;
    }

    public void setTypeFrom(String typeFrom) {
        this.typeFrom = typeFrom;
    }

    public String getTypeTo() {
        return typeTo;
    }

    public void setTypeTo(String typeTo) {
        this.typeTo = typeTo;
    }

    public Integer getLimitPerUser() {
        return limitPerUser;
    }

    public void setLimitPerUser(Integer limitPerUser) {
        this.limitPerUser = limitPerUser;
    }

    public Integer getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(Integer couponLimit) {
        this.couponLimit = couponLimit;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }
}
