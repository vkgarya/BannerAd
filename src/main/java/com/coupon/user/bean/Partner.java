package com.coupon.user.bean;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;

    private String name;

    @JsonProperty("merchant_id")
    private String merchantId;

    @JsonProperty("company_name")
    private String companyName;

    private String status;

    private String email;

    @JsonProperty("phone_no")
    private String phoneNo;

    private String website;

    @JsonProperty("nature_of_business")
    private String natureOfBusiness;

    private String address;

    private List<UserDTO> users;

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

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company_name='" + companyName + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", phone_no='" + phoneNo + '\'' +
                ", website='" + website + '\'' +
                ", nature_of_business='" + natureOfBusiness + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
