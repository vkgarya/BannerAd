package com.coupon.partner.payload;

public class AllPartnersRequest {
    private String txn_id;

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    @Override
    public String toString() {
        return "AllPartnersRequest{" +
                "txn_id='" + txn_id + '\'' +
                '}';
    }
}
