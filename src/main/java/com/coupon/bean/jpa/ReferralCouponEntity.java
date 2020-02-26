package com.coupon.bean.jpa;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.coupon.utils.UTCDateTimeConverter;

@Entity
@Table(name = "referral_coupon")
public class ReferralCouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name="created_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdOn;

    @Column(name="started_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime startedOn;

    @Column(name="closed_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime closedOn;

    @Column(name="active")
    private Boolean active = true;

    @OneToMany(mappedBy = "referralCouponEntity", targetEntity = ReferralBonusMappingEntity.class)
    private List<ReferralBonusMappingEntity> referralBonusMappingEntityList;

    @OneToMany(mappedBy = "referralCouponEntity", targetEntity = ReferralCouponCodeLanguageMappingEntity.class)
    private List<ReferralCouponCodeLanguageMappingEntity> listOfCouponCodes;

    @OneToMany(mappedBy = "referralCouponEntity", targetEntity = ReferralCouponDescriptionLanguageMappingEntity.class)
    private List<ReferralCouponDescriptionLanguageMappingEntity> listOfCouponDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ReferralBonusMappingEntity> getReferralBonusMappingEntityList() {
        return referralBonusMappingEntityList;
    }

    public void setReferralBonusMappingEntityList(List<ReferralBonusMappingEntity> referralBonusMappingEntityList) {
        this.referralBonusMappingEntityList = referralBonusMappingEntityList;
    }

    public List<ReferralCouponCodeLanguageMappingEntity> getListOfCouponCodes() {
        return listOfCouponCodes;
    }

    public void setListOfCouponCodes(List<ReferralCouponCodeLanguageMappingEntity> listOfCouponCodes) {
        this.listOfCouponCodes = listOfCouponCodes;
    }

    public List<ReferralCouponDescriptionLanguageMappingEntity> getListOfCouponDesc() {
        return listOfCouponDesc;
    }

    public void setListOfCouponDesc(List<ReferralCouponDescriptionLanguageMappingEntity> listOfCouponDesc) {
        this.listOfCouponDesc = listOfCouponDesc;
    }
}
