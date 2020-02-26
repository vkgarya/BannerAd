package com.coupon.ad.bean;

import java.io.Serializable;
import java.util.Date;

public class AdClickDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String adTxnId;
    private Integer adId;
    private Integer clickCount;
    private Date clickDate;
    private Date clickTime;
    private String userid;
    private String remoteip;


    private Date expiredOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdTxnId() {
        return adTxnId;
    }

    public void setAdTxnId(String adTxnId) {
        this.adTxnId = adTxnId;
    }

    public Integer getAdId() {
        return adId;
    }

    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    public Date getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(Date expiredOn) {
        this.expiredOn = expiredOn;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Date getClickDate() {
        return clickDate;
    }

    public void setClickDate(Date clickDate) {
        this.clickDate = clickDate;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRemoteip() {
        return remoteip;
    }

    public void setRemoteip(String remoteip) {
        this.remoteip = remoteip;
    }

    @Override
    public String toString() {
        return "AdClickDTO{" +
                "id=" + id +
                ", adTxnId='" + adTxnId + '\'' +
                ", adId=" + adId +
                ", clickCount=" + clickCount +
                ", clickDate=" + clickDate +
                ", clickTime=" + clickTime +
                ", userid='" + userid + '\'' +
                ", remoteip='" + remoteip + '\'' +
                ", expiredOn=" + expiredOn +
                '}';
    }
}

