package com.coupon.ad.bean.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ad_request")
public class AdRequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type",  length = 30)
    private String type;

    @Column(name = "user_id",  length = 30)
    private String userid;

    @Column(name = "req_txn_id", length = 50)
    private String reqTxnid;

    @Column(name = "ad_txn_id", length = 50)
    private String adTxnid;

    @Column(name = "remote_ip", nullable = false, length = 50)
    private String remoteip;

    @Temporal(TemporalType.DATE)
    @Column(name = "req_date", nullable = false, length = 50)
    private Date reqDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "req_time", nullable = false, length = 50)
    private Date reqTime;


    @Column(name = "status_code", nullable = false, length = 10)
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
        return "AdRequestEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", userid='" + userid + '\'' +
                ", reqTxnid='" + reqTxnid + '\'' +
                ", adTxnid='" + adTxnid + '\'' +
                ", remoteip='" + remoteip + '\'' +
                ", reqDate=" + reqDate +
                ", reqTime=" + reqTime +
                ", statusCode=" + statusCode +
                '}';
    }
}
