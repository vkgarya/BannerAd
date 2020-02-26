package com.coupon.auth.payload;

import com.coupon.bean.StatusDTO;

public class AddUserResponse {
    private String status;
    private String message;
    private Integer status_code;
    private String txn_id;
    private StatusDTO statusDTO;

    public AddUserResponse(String txn_id, StatusDTO statusDTO) {
        this.txn_id = txn_id;
        this.status = statusDTO.getStatus();
        this.message = statusDTO.getDescription();
        this.status_code = statusDTO.getStatus_code();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

}
