package com.coupon.ad.payload;

public class AdRequest {

    private String user_id;
    private String ad_type;
    private String txn_id;
    private String country;
    private String state;
    private Integer age;
    private String gender;
    private String genre;
    private String nationality;

    private Integer limit;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAd_type() {
        return ad_type;
    }

    public void setAd_type(String ad_type) {
        this.ad_type = ad_type;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "AdRequest{" +
                "user_id='" + user_id + '\'' +
                ", ad_type='" + ad_type + '\'' +
                ", txn_id='" + txn_id + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", genre='" + genre + '\'' +
                ", nationality='" + nationality + '\'' +
                ", limit=" + limit +
                '}';
    }
}
