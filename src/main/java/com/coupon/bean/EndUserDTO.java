package com.coupon.bean;

import java.io.Serializable;
import java.util.Date;

import com.coupon.bean.jpa.EndUserEntity;

public class EndUserDTO implements Serializable {
    private String user_id;

    private String gender;

    private String nationality;

    private String location;

    private Date dob;

    private String referrer_code;

    private String user_referrer_code;

    private String occupation_type;

    private String salary_range;

    public EndUserDTO() {
        super();
    }

    public EndUserDTO(EndUserEntity data) {
        super();
        this.user_id = data.getUserId();
        this.gender = data.getGender();
        this.nationality = data.getNationality();
        this.location = data.getLocation();
        this.dob = data.getDob();
        this.referrer_code = data.getReferrerCode();
        this.user_referrer_code = data.getUserReferrerCode();
        this.occupation_type = data.getOccupationType();
        this.salary_range = data.getSalaryRange();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getReferrer_code() {
        return referrer_code;
    }

    public void setReferrer_code(String referrer_code) {
        this.referrer_code = referrer_code;
    }

    public String getUser_referrer_code() {
        return user_referrer_code;
    }

    public void setUser_referrer_code(String user_referrer_code) {
        this.user_referrer_code = user_referrer_code;
    }

    public String getOccupation_type() {
        return occupation_type;
    }

    public void setOccupation_type(String occupation_type) {
        this.occupation_type = occupation_type;
    }

    public String getSalary_range() {
        return salary_range;
    }

    public void setSalary_range(String salary_range) {
        this.salary_range = salary_range;
    }
}
