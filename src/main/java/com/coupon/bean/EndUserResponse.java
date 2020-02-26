package com.coupon.bean;

import java.io.Serializable;

public class EndUserResponse implements Serializable {
    private String status;
    private String message;
    private Integer status_code;
    private EndUserDTO user_details;

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

    public EndUserDTO getUser_details() {
        return user_details;
    }

    public void setUser_details(EndUserDTO user_details) {
        this.user_details = user_details;
    }
}
