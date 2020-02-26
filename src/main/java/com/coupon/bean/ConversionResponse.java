package com.coupon.bean;

public class ConversionResponse {
    private String status;
    private Integer status_code;
    private String description;
    private String txn_id;

    public ConversionResponse(StatusDTO statusDTO, String txnId) {
        super();
        this.txn_id = txnId;
        this.status = statusDTO.getStatus();
        this.status_code = statusDTO.getStatus_code();
        this.description = statusDTO.getDescription();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }
}
