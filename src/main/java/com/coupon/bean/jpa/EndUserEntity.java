package com.coupon.bean.jpa;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.coupon.bean.CreateUserRequest;
import com.coupon.utils.TimeUtil;
import com.coupon.utils.UTCDateTimeConverter;

@Entity
@Table(name = "end_user")
public class EndUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "location")
    private String location;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @Column(name="referrer_code")
    private String referrerCode;

    @Column(name="user_referrer_code")
    private String userReferrerCode;

    @Column(name="occupation_type")
    private String occupationType;

    @Column(name="salary_range")
    private String salaryRange;

    @Column(name="created_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdOn;

    public EndUserEntity() {
        super();
    }

    public EndUserEntity(CreateUserRequest requestBody) {
        super();
        this.setUserId(requestBody.getUser_id());
        this.setReferrerCode(requestBody.getReferral_code());
        this.setGender(requestBody.getGender());
        this.setDob(requestBody.getDob());
        this.setNationality(requestBody.getNationality());
        this.setLocation(requestBody.getLocation());
        this.setCreatedOn(TimeUtil.getCurrentUTCTime());
        this.setOccupationType(requestBody.getOccupation_type());
        this.setSalaryRange((requestBody.getSalary_range()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getReferrerCode() {
        return referrerCode;
    }

    public void setReferrerCode(String referrerCode) {
        this.referrerCode = referrerCode;
    }

    public String getUserReferrerCode() {
        return userReferrerCode;
    }

    public void setUserReferrerCode(String userReferrerCode) {
        this.userReferrerCode = userReferrerCode;
    }

    public String getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(String occupationType) {
        this.occupationType = occupationType;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
