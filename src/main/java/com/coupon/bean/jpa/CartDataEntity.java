package com.coupon.bean.jpa;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.coupon.utils.UTCDateTimeConverter;

@Entity
@Table(name = "cart_data")
public class CartDataEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "txn_id", nullable = false)
    private String txnId;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "invoice_amount", nullable = false)
    private Double invoiceAmount;

    @Column(name="created_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime createdOn;

    @Column(name="updated_on")
    @Convert(converter = UTCDateTimeConverter.class)
    private ZonedDateTime updateOn;

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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(ZonedDateTime updateOn) {
        this.updateOn = updateOn;
    }
}
