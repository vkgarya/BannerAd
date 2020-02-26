package com.coupon.user.bean.jpa;

import com.coupon.user.bean.Partner;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "partner", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class PartnerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "merchant_id", nullable = false, length = 45)
    private String merchantId;

    @Column(name = "company_name", nullable = false, length = 45)
    private String companyName;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "email", nullable = false, length = 20)
    private String email;


    @Column(name = "phone_no", nullable = false, length = 20)
    private String phoneNo;

    @Column(name = "website", nullable = false, length = 20)
    private String website;

    @Column(name = "nature_of_business", nullable = false, length = 20)
    private String natureOfBusiness;

    @Column(name = "address", nullable = false, length = 20)
    private String address;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PartnerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", website='" + website + '\'' +
                ", natureOfBusiness='" + natureOfBusiness + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
