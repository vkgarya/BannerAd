package com.coupon.partner.payload;

public class AddPartnerRequest {

    private Integer id;
    private String name;
    private String company_name;
    private String status;
    private String email;
    private String phone_no;
    private String website;
    private String nature_of_business;
    private String address;
    private  String txn_id;

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

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNature_of_business() {
        return nature_of_business;
    }

    public void setNature_of_business(String nature_of_business) {
        this.nature_of_business = nature_of_business;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    @Override
    public String toString() {
        return "AddPartnerRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company_name='" + company_name + '\'' +
                ", status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", website='" + website + '\'' +
                ", nature_of_business='" + nature_of_business + '\'' +
                ", address='" + address + '\'' +
                ", txn_id='" + txn_id + '\'' +
                '}';
    }
}
