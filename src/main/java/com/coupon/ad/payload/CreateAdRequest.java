package com.coupon.ad.payload;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;
import java.util.List;

public class CreateAdRequest {
    private int id;
    private Integer partner_id;
    private String country;
    private String state;
    private String click_type;
    private String click_url;
    private String type;
    private String status;
    private List<String> age_groups;
    private List<String> genders;
    private List<String> file_resolutions;
    private List<Integer> deleted_asset_ids;
    private String genre;
    private String nationality;
    private int priority;
    private int balance_clicks;
    private Double balance_amount;
    private int coupon_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime started_on;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime closed_on;
    private String txn_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
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

    public String getClick_type() {
        return click_type;
    }

    public void setClick_type(String click_type) {
        this.click_type = click_type;
    }

    public String getClick_url() {
        return click_url;
    }

    public void setClick_url(String click_url) {
        this.click_url = click_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAge_groups() {
        return age_groups;
    }

    public void setAge_groups(List<String> age_groups) {
        this.age_groups = age_groups;
    }

    public List<String> getGenders() {
        return genders;
    }

    public void setGenders(List<String> genders) {
        this.genders = genders;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getBalance_clicks() {
        return balance_clicks;
    }

    public void setBalance_clicks(int balance_clicks) {
        this.balance_clicks = balance_clicks;
    }

    public Double getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(Double balance_amount) {
        this.balance_amount = balance_amount;
    }

    public ZonedDateTime getStarted_on() {
        return started_on;
    }

    public void setStarted_on(ZonedDateTime started_on) {
        this.started_on = started_on;
    }

    public ZonedDateTime getClosed_on() {
        return closed_on;
    }

    public void setClosed_on(ZonedDateTime closed_on) {
        this.closed_on = closed_on;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public List<String> getFile_resolutions() {
        return file_resolutions;
    }

    public void setFile_resolutions(List<String> file_resolutions) {
        this.file_resolutions = file_resolutions;
    }

    public List<Integer> getDeleted_asset_ids() {
        return deleted_asset_ids;
    }

    public void setDeleted_asset_ids(List<Integer> deleted_asset_ids) {
        this.deleted_asset_ids = deleted_asset_ids;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    @Override
    public String toString() {
        return "CreateAdRequest{" +
                "id=" + id +
                ", partner_id=" + partner_id +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", click_type='" + click_type + '\'' +
                ", click_url='" + click_url + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", age_groups=" + age_groups +
                ", genders=" + genders +
                ", file_resolutions=" + file_resolutions +
                ", deleted_asset_ids=" + deleted_asset_ids +
                ", genre='" + genre + '\'' +
                ", nationality='" + nationality + '\'' +
                ", priority=" + priority +
                ", balance_clicks=" + balance_clicks +
                ", balance_amount=" + balance_amount +
                ", coupon_id=" + coupon_id +
                ", started_on=" + started_on +
                ", closed_on=" + closed_on +
                ", txn_id='" + txn_id + '\'' +
                '}';
    }
}
