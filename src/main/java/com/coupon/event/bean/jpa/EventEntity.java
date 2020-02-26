package com.coupon.event.bean.jpa;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.coupon.utils.TimeUtil;
import com.coupon.utils.UTCDateTimeConverter;

@Entity
@Table(name = "events")
public class EventEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "txn_id", nullable = false)
    private String txnId;

    @Column(name = "custom_event_id", length = 5, nullable = false)
    private Integer customEventId;

    @Column(name = "user_id", length = 50, nullable = false)
    private String userId;

    @Column(name="inserted_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime insertedOn;

    public EventEntity() {
        super();
        this.insertedOn = TimeUtil.getCurrentUTCTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCustomEventId() {
        return customEventId;
    }

    public void setCustomEventId(Integer customEventId) {
        this.customEventId = customEventId;
    }

    public ZonedDateTime getInsertedOn() {
        return insertedOn;
    }

    public void setInsertedOn(ZonedDateTime insertedOn) {
        this.insertedOn = insertedOn;
    }
}
