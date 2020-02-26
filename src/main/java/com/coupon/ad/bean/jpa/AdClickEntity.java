package com.coupon.ad.bean.jpa;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ad_click")
public class AdClickEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "ad_txn_id", nullable = false, length = 30)
    private String adTxnId;

    @Column(name = "ad_id", nullable = false, length = 30)
    private Integer adId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expired_on", nullable = true, length = 30)
    private Date expiredOn;

    @Column(name = "user_id",  length = 30)
    private String userid;

    @Column(name = "remote_ip", nullable = false, length = 50)
    private String remoteip;


    @Column(name = "click_count",  length = 10)
    private Integer clickCount=0;

    @Temporal(TemporalType.DATE)
    @Column(name = "click_date", nullable = true, length = 50)
    private Date clickDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "click_time", nullable = true, length = 50)
    private Date clickTime;

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

    @Override
    public String toString() {
        return "AdClickEntity{" +
                "id=" + id +
                ", adTxnId='" + adTxnId + '\'' +
                ", adId=" + adId +
                ", expiredOn=" + expiredOn +
                ", userid='" + userid + '\'' +
                ", remoteip='" + remoteip + '\'' +
                ", clickCount=" + clickCount +
                ", clickDate=" + clickDate +
                ", clickTime=" + clickTime +
                '}';
    }
}
