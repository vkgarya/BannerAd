package com.coupon.bean;

import java.io.Serializable;
import java.util.List;

import com.coupon.constants.Status;

public class ConversionRequest implements Serializable {
    private String txn_id;
    private String msg;
    private List<Integer> coupon_codes;
    private Status status;
    private Double rewards_used;

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Integer> getCoupon_codes() {
        return coupon_codes;
    }

    public void setCoupon_codes(List<Integer> coupon_codes) {
        this.coupon_codes = coupon_codes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getRewards_used() {
        return rewards_used;
    }

    public void setRewards_used(Double rewards_used) {
        this.rewards_used = rewards_used;
    }
}
