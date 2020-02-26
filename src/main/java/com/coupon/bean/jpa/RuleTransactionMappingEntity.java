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

import com.coupon.constants.CalenderType;
import com.coupon.constants.Condition;
import com.coupon.constants.Relation;
import com.coupon.utils.UTCDateTimeConverter;

@Entity
@Table(name = "rule_transaction_mapping")
public class RuleTransactionMappingEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "coupon_id_ref", referencedColumnName = "id")
    private CouponEntity couponEntity;

    @Column(name = "cal_type")
    @Enumerated(EnumType.STRING)
    private CalenderType calType;

    @Column(name = "type_from")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime typeFrom;

    @Column(name = "type_to")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime typeTo;

    @Column(name = "transaction_number")
    private String transactionNumber;

    @Column(name = "condition_type")
    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Column(name = "relation")
    @Enumerated(EnumType.STRING)
    private Relation relation;

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

    public ZonedDateTime getTypeFrom() {
        return typeFrom;
    }

    public void setTypeFrom(ZonedDateTime typeFrom) {
        this.typeFrom = typeFrom;
    }

    public ZonedDateTime getTypeTo() {
        return typeTo;
    }

    public void setTypeTo(ZonedDateTime typeTo) {
        this.typeTo = typeTo;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
