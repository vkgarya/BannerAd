package com.coupon.ad.bean;

import com.coupon.ad.bean.jpa.AdEntity;
import com.coupon.ad.constants.AdType;
import com.coupon.ad.payload.CreateAdRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int coupon_id = 0;
    private String type;
    private String click_url;
    private String click_type;
    private Integer partner_id;
    private String status;
    private Integer balance_clicks = 0;
    private Double balance_amount = 0.0;
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime started_on;

    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime closed_on;

    private List<AdAsset> adAssetList;

    private List<AdAsset> banners;

    private List<AdAsset> videos;

    @JsonIgnore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClick_url() {
        return click_url;
    }

    public void setClick_url(String click_url) {
        this.click_url = click_url;
    }

    public String getClick_type() {
        return click_type;
    }

    public void setClick_type(String click_type) {
        this.click_type = click_type;
    }

    public void setAdAssetList(List<AdAsset> adAssetList) {
        this.adAssetList = adAssetList;
        if (AdType.BANNER.equalsIgnoreCase(this.type) && !adAssetList.isEmpty()) {
            setBanners(adAssetList);
        }
        if (AdType.VIDEO.equalsIgnoreCase(this.type) && !adAssetList.isEmpty()) {
            setVideos(adAssetList);
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<AdAsset> getBanners() {
        return banners;
    }

    public void setBanners(List<AdAsset> banners) {
        this.banners = banners;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<AdAsset> getVideos() {
        return videos;
    }

    public void setVideos(List<AdAsset> videos) {
        this.videos = videos;
    }

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    @JsonIgnore
    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
    }

    @JsonIgnore
    public ZonedDateTime getStarted_on() {
        return started_on;
    }

    public void setStarted_on(ZonedDateTime started_on) {
        this.started_on = started_on;
    }

    @JsonIgnore
    public ZonedDateTime getClosed_on() {
        return closed_on;
    }

    public void setClosed_on(ZonedDateTime closed_on) {
        this.closed_on = closed_on;
    }

    @JsonIgnore
    public String getStatus() {
        return status;
    }


    @JsonIgnore
    public Integer getBalance_clicks() {
        return balance_clicks;
    }

    public void setBalance_clicks(Integer balance_clicks) {
        this.balance_clicks = balance_clicks;
    }

    @JsonIgnore
    public Double getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(Double balance_amount) {
        this.balance_amount = balance_amount;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", coupon_id=" + coupon_id +
                ", type='" + type + '\'' +
                ", click_url='" + click_url + '\'' +
                ", click_type='" + click_type + '\'' +
                ", partner_id=" + partner_id +
                ", status=" + status +
                ", started_on=" + started_on +
                ", closed_on=" + closed_on +
                ", adAssetList=" + adAssetList +
                ", banners=" + banners +
                ", videos=" + videos +
                '}';
    }

    public Ad() {
        super();
    }

    public Ad(CreateAdRequest createAdRequest) {
        super();
        this.id = createAdRequest.getId();
        this.click_type = createAdRequest.getClick_type();
        this.click_url = createAdRequest.getClick_url();
        this.type = createAdRequest.getType();
        this.status = createAdRequest.getStatus();
        this.partner_id = createAdRequest.getPartner_id();
        this.started_on = createAdRequest.getStarted_on();
        this.closed_on = createAdRequest.getClosed_on();
    }

    public Ad getAd(AdEntity adEntity) {
        Ad ad = new Ad();
        ad.setId(adEntity.getId());
        ad.setStatus(adEntity.getStatus());
        ad.setCoupon_id(adEntity.getCoupon_id());
        ad.setType(adEntity.getType());
        ad.setClick_type(adEntity.getClick_type());
        ad.setClick_url(adEntity.getClick_url());
        ad.setStarted_on(adEntity.getStarted_on());
        ad.setClosed_on(adEntity.getClosed_on());
        ad.setPartner_id(adEntity.getPartner_id());
        ad.setBalance_amount(adEntity.getBalance_amount());
        ad.setBalance_clicks(adEntity.getBalance_clicks());
        return ad;
    }
}
