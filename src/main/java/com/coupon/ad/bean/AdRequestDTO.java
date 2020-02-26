package com.coupon.ad.bean;

import com.coupon.ad.payload.AdRequest;
import com.coupon.utils.CampaignUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;

public class AdRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String type;
    private String userid;
    private String reqTxnid;
    private String adTxnid;
    private String remoteip;
    private Date reqDate;
    private Date reqTime;
    private Integer statusCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReqTxnid() {
        return reqTxnid;
    }

    public void setReqTxnid(String reqTxnid) {
        this.reqTxnid = reqTxnid;
    }

    public String getAdTxnid() {
        return adTxnid;
    }

    public void setAdTxnid(String adTxnid) {
        this.adTxnid = adTxnid;
    }

    public String getRemoteip() {
        return remoteip;
    }

    public void setRemoteip(String remoteip) {
        this.remoteip = remoteip;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "AdRequestDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", userid='" + userid + '\'' +
                ", reqTxnid='" + reqTxnid + '\'' +
                ", adTxnid='" + adTxnid + '\'' +
                ", remoteip='" + remoteip + '\'' +
                ", reqDate=" + reqDate +
                ", reqTime=" + reqTime +
                ", statusCode='" + statusCode + '\'' +
                '}';
    }

    public AdRequestDTO() {
        super();
    }

    public AdRequestDTO(AdRequest adRequest, HttpServletRequest request) {
        super();
        this.adTxnid = CampaignUtil.getRandomTxnIdForAdRequest();
        this.type = adRequest.getAd_type();
        this.reqDate = new Date();
        this.reqTime = new Date();
        this.remoteip = CampaignUtil.getClientIP(request);
        this.userid = adRequest.getUser_id();
        this.reqTxnid = adRequest.getTxn_id();
    }
}
