package com.coupon.bean;

import java.io.Serializable;

public class CouponDeailsRequest implements Serializable {
    private String txn_id;
    private String code;

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
